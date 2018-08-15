package com.mc.rabbitmq;

/**
 * 自定义消息接收类
 * @author lc
 */
public class Receive {

    public void handleMessage(String message){
        System.err.println("handleMessage: "+ message);
    }

    public void process(String msg){
        System.err.println("Process: "+ msg);
    }
}
