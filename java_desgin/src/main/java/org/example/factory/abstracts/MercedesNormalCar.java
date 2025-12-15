package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:23
 */

public class MercedesNormalCar implements NormalCar {

    @Override
    public void createNormalCar() {
        System.out.println("生产奔驰轿车");
    }
}
