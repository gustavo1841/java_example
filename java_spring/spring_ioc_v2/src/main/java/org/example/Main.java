package org.example;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.beans.BeanDefinition;
import org.example.beans.PropertyValue;
import org.example.beans.RuntimeBeanRefence;
import org.example.beans.TypeStringValue;
import org.example.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/18 10:17
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();
    Map<String,Object> singletonMap = new HashMap<>();

    public InputStream getResouce(String name){
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }

    @BeforeEach
    public void init(){
        InputStream is = getResouce("beans.xml");
        Document doc = createDocment(is);
        parserBeanDefinitions(doc.getRootElement());
    }

    private Document createDocment(InputStream is) {
        Document read = null;
        try {
            read = new SAXReader().read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return read;
    }

    private void parserBeanDefinitions(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements){
            paserBeanDefinition(element);
        }
    }

    private void paserBeanDefinition(Element element) {
        BeanDefinition beanDefinition = new BeanDefinition();
        String beanName = element.attributeValue("id");
        String aClass = element.attributeValue("class");
        if(beanName == null || beanName.isEmpty()){
            return;
        }
        String scope = element.attributeValue("scope");
        String initMethod = element.attributeValue("init-method");

        beanDefinition.resovleClass(aClass);
        beanDefinition.setBeanName(beanName);
        beanDefinition.setScope(scope);
        beanDefinition.setInitMethod(initMethod);

        processPropertyValues(beanDefinition,element.elements());

        beanDefinitionMap.put(beanName,beanDefinition);
    }


    private void processPropertyValues(BeanDefinition beanDefinition, List<Element> elements) {
        List<PropertyValue> propertyValues = new ArrayList<>();
        for (Element element : elements){
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            String ref = element.attributeValue("ref");
            PropertyValue propertyValue = new PropertyValue();
            propertyValue.setName(name);
            if (value != null && !value.equals("")){
                TypeStringValue tsv = new TypeStringValue();
                tsv.setValue(value);
                tsv.setTargetType(resvoleType(beanDefinition.getClazzType(),name));
                propertyValue.setValue(tsv);
            }else{
                RuntimeBeanRefence refBean = new RuntimeBeanRefence();
                refBean.setRef(ref);
                propertyValue.setName(name);
                propertyValue.setValue(refBean);
            }
            propertyValues.add(propertyValue);
        }
        beanDefinition.setPropertyValues(propertyValues);
    }

    private Class<?> resvoleType(Class<?> clazzType, String name) {
        try {
            Field field = clazzType.getDeclaredField(name);
            return field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String name){
        Object bean = singletonMap.get(name);
        if(Objects.nonNull(bean)){
            return bean;
        }

        BeanDefinition bd = beanDefinitionMap.get(name);
        if(Objects.isNull(bd)){
            return null;
        }
        if(bd.isSingleton()){
            bean = createBean(bd);
            singletonMap.put(name,bean);
            return bean;
        }else if(bd.isPrototype()){
            return createBean(bd);
        }

        return bean;
    }

    private Object createBean(BeanDefinition bd) {
//        创建对象
        Object bean = createInstance(bd);
//        反射赋值
        populateBean(bean,bd);
//        调用初始化方法
        initlizingBean(bean,bd);
        return bean;
    }

    private void initlizingBean(Object bean, BeanDefinition bd) {
        String initMethod = bd.getInitMethod();
        if(initMethod == null || initMethod.isEmpty()){
            return;
        }
        invokeMethod(bd.getClazzType(),bd.getInitMethod(), bean);
    }

    private void invokeMethod(Class clazz, String initMethod,Object bean) {
        try {
            Method method = clazz.getMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateBean(Object bean, BeanDefinition bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues){
            String name = pv.getName();
            Object value = pv.getValue();
            Object valueToUse = parseValue(value);
//            依赖注入
            setPropertyValue(bd.getClazzType(),name,bean,valueToUse);
        }
    }

    private void setPropertyValue(Class<?> clazzType, String name, Object bean, Object valueToUse) {
        try {
            Field field = clazzType.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean,valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object parseValue(Object value) {
        if(value instanceof TypeStringValue){
            TypeStringValue tv = (TypeStringValue) value;
            String stringValue = tv.getValue();
            Class<?> targetType = tv.getTargetType();
            return handleType(stringValue,targetType);
        }else if(value instanceof RuntimeBeanRefence){
            RuntimeBeanRefence ref = (RuntimeBeanRefence) value;
//            容易出现循环依赖的问题   ----->spring如何解决的？三级缓存
            return getBean(ref.getRef());
        }
        return null;
    }

    private Object handleType(String stringValue, Class<?> targetType) {
        if(targetType == String.class){
            return stringValue;
        }else if(targetType == Integer.class){
            return Integer.parseInt(stringValue);
        }
        return null;
    }

    private Object createInstance(BeanDefinition bd) {
        return newInstance(bd.getClazzType());
    }

    private Object newInstance(Class clazzType) {
        try {
            Constructor constructor = clazzType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void run() {
        /**
         * 1、编写外部文件，定义相关引用信息
         * 2、资源获取
         *    1）文件资源获取
         *    2）网络资源获取
         * 3、创建文档对象
         * 4、解析文档对象
         * 5、创建Bean
         * 6、注入属性
         * 7、存储Bean对象
         * 8、获取Bean对象
         * 9、使用Bean对象
         */
        UserService userService =(UserService) getBean("userService");
        userService.add();
    }
}