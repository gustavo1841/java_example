package org.example.factory.method;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:07
 */

public class SuvFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new Suv();
    }
}
