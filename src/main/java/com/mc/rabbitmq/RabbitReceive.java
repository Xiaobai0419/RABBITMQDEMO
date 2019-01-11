package com.mc.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 固定订阅某个Queue,当同时订阅时，因为不是广播，所以会随机消费--由于QueueName有另外一个监听（消费者），所以每次只有一个监听会被执行，也就是只有一个消费者能消费到队列消息！！
 * @author lc
 */
@Component
@RabbitListener(queues = "QueueName")
public class RabbitReceive {

    @RabbitHandler
    public void processMessage(String content){
        System.err.println(content+ "~~~~~~~~~~~~~~~~~~~~another Listener,of QueueName queue!!!!");
    }
}
