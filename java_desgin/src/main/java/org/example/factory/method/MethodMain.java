package org.example.factory.method;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:08
 */

public class MethodMain {
    public static void main(String[] args) {
        Car car = new SedanFactory().createCar();
        car.drive();
        Car suv = new SuvFactory().createCar();
        suv.drive();
    }
}
