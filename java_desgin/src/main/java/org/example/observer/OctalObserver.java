package org.example.observer;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 16:48
 */

public class OctalObserver extends Observer{

    public OctalObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Octal String: "
                + Integer.toOctalString( subject.getState() ) );
    }
}
