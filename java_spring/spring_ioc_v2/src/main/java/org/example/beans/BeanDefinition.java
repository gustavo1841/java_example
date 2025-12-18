package org.example.beans;

import lombok.Data;

import java.util.List;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/18 10:29
 */
@Data
public class BeanDefinition {
    private String beanName;
    private String className;
    private Class<?> clazzType;
    private String scope;
    private String initMethod;
    private List<PropertyValue> propertyValues;


    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    public boolean isSingleton(){
        return SCOPE_SINGLETON.equals(this.scope);
    }
    public boolean isPrototype(){
        return SCOPE_PROTOTYPE.equals(this.scope);
    }

    public void resovleClass(String clazz){
        try {
            this.clazzType = Class.forName(clazz);
            this.className = this.clazzType.getSimpleName();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
