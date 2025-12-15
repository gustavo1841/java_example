package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:34
 */

public class MercedesFactory implements AbstracFactory{
    @Override
    public NormalCar createNormalCar() {
        return new MercedesNormalCar();
    }

    @Override
    public SuvCar createSuvCar() {
        return new MercedesSuvCar();
    }
}
