package org.example.observer;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 16:49
 */

public class HexaObserver extends Observer{

    public HexaObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Hex String: "
                + Integer.toHexString( subject.getState() ).toUpperCase() );
    }
}
