package org.example.observer;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 16:47
 */

public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
