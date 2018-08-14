package com.mc.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceive {

    @RabbitListener(queues = "QueueName")
    public void processMessage(String content){
        System.err.println(content);
    }
}
