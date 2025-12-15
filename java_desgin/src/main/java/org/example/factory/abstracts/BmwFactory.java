package org.example.factory.abstracts;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/15 21:48
 */

public class BmwFactory implements AbstracFactory{
    @Override
    public NormalCar createNormalCar() {
        return new BmwNormalCar();
    }

    @Override
    public SuvCar createSuvCar() {
        return new MercedesSuvCar();
    }
}
