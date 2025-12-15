package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:18
 */

public class BmwSuvCar implements SuvCar {

    @Override
    public void createSuvCar() {
        System.out.println("创建宝马SUV");
    }
}
