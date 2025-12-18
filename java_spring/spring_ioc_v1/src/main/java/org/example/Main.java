package org.example;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/17 22:33
 */

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultDocument;
import org.example.beans.BeanDefinition;
import org.example.beans.PropertyValue;
import org.example.user.UserService;
import org.example.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     * 刚入行
     * @return
     */
    public UserService getUserService(){
        return new UserServiceImpl();
    }
    @Test
    public void rookieDev(){
        System.out.println("==============刚入行程序员==============");
        UserService userService = getUserService();
        userService.addUser();
    }

    /**
     * 工作1~2年的程序员
     */
    public Object getBeanMiddle(String name){
        if("userService".equals(name)){
//            可设置一些属性值
            return new UserServiceImpl();
        }
        return null;
    }
    @Test
    public void middleDev(){
        System.out.println("==============工作1~2年的程序员==============");
        UserService userService =(UserService)getBeanMiddle("userService");
        userService.addUser();
    }

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    public InputStream gerResouce(String location){
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
    @BeforeEach
    public void init()throws Exception{
//        1、读取资源位置
        InputStream is  = gerResouce("beans.xml");
//        2、创建并解析文档对象
        Document read = new SAXReader().read(is);
        Element rootElement = read.getRootElement();
        List<Element> elements = rootElement.elements();
        for(int i=0;i<elements.size();i++){
            Element element = elements.get(i);
            String name = element.attributeValue("id");
            String className = element.attributeValue("class");
            String scope = element.attributeValue("scope");
            List<PropertyValue> propertyValues = new ArrayList<>();
            List<Element> attrs = element.elements();
            for (int j=0;j<attrs.size();j++){
                Element attr = attrs.get(j);
                String n = attr.attributeValue("name");
                String v = attr.attributeValue("ref");
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setName(n);
                propertyValue.setValue(v);
                propertyValues.add(propertyValue);
            }
            //        3、为Bean赋值
            try {
                Class<?> clazz = Class.forName(className);
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setClazz(clazz);
                beanDefinition.setClassName(clazz.getName());
                beanDefinition.setScope(scope);
                beanDefinition.setName(name);
                beanDefinition.setPropertyValues(propertyValues);
                beanDefinitionMap.put(name,beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Class<?> getBeanGrade(String name){
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        return beanDefinition.getClazz();
    }

    @Test
    public void gradeDev()throws Exception{
        System.out.println("==============1~3年程序员==============");
//        要存好并管理这些引用信息，我们最好的方式就是引用一些外部的配置文件

//        配置文件需要保存什么样的信息？
        /**
         * 引用名称、全限定名称、属性元素、初始化方法、销毁方法、节点类型
         */
//        如何加载
        /**
         * 通过反射加载，之后可以通过一个map存储起来、
         */
//        如何使用？
        /**
         * 通过map.get(key)获取
         */
        Class<?> clazz = getBeanGrade("userService");
        UserService userService =(UserService) clazz.newInstance();
        userService.addUser();
    }

}
