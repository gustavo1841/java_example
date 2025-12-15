package org.example.factory.method;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:00
 */

public class Sedan implements Car{
    @Override
    public void drive() {
        System.out.println("生产轿车");
    }
}
