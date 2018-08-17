package com.mc.rabbitmq;

import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitClientwrapper {
    private AmqpManagementOperations amqpManagementOperations;
    @Autowired
    public void setAmqpManagementOperations(AmqpManagementOperations amqpManagementOperations) {
        this.amqpManagementOperations = amqpManagementOperations;
    }
}
