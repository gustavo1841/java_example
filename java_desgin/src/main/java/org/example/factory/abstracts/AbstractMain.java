package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:51
 */

public class AbstractMain {
    public static void main(String[] args) {
        AbstracFactory mercedesFactory = new MercedesFactory();
        NormalCar mercedesNormalCar = mercedesFactory.createNormalCar();
        SuvCar mercedesSuvCar = mercedesFactory.createSuvCar();
        mercedesNormalCar.createNormalCar();
        mercedesSuvCar.createSuvCar();

        AbstracFactory bmwFactory = new BmwFactory();
        NormalCar bmwNormalCar = bmwFactory.createNormalCar();
        SuvCar bmwSuvCar = bmwFactory.createSuvCar();
        bmwNormalCar.createNormalCar();
        bmwSuvCar.createSuvCar();

    }
}
