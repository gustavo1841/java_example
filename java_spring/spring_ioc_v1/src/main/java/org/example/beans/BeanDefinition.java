package org.example.beans;

import lombok.Data;

import java.util.List;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/17 23:33
 */
@Data
public class BeanDefinition {
    private String name;
    private String scope;
    private String className;
    private Class<?> clazz;
    private String initMethod;
    private List<PropertyValue> propertyValues;
}
