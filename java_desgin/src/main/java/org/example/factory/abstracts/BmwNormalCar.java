package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:16
 */

public class BmwNormalCar implements NormalCar {

    @Override
    public void createNormalCar() {
        System.out.println("创建宝马轿车");
    }
}
