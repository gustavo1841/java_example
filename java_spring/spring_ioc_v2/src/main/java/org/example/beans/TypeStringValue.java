package org.example.beans;

import lombok.Data;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/18 14:56
 */
@Data
public class TypeStringValue {
    private String value;
//    值对象类型
    private Class<?> targetType;
}
