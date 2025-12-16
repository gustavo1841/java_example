package org.example.observer;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 16:48
 */

public class BinaryObserver extends Observer{

    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Binary String: "
                + Integer.toBinaryString( subject.getState() ) );
    }
}
