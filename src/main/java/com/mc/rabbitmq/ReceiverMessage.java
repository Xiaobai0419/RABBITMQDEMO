package com.mc.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接受消息
 * @author lc
 * @date 2018/3/15
 */
@Component
public class ReceiverMessage {
    private RabbitAdmin rabbitAdmin;
    @Autowired
    public void setRabbitAdmin(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


}
