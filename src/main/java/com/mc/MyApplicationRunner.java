package com.mc;

import com.mc.rabbitmq.Receive;
import com.mc.util.SpringUtil;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态订阅
 */
@Component
@Order(10)
public class MyApplicationRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        SimpleMessageListenerContainer container = SpringUtil.getBean(SimpleMessageListenerContainer.class);
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new Receive());
        messageListenerAdapter.addQueueOrTagToMethodName("QueueName","handleMessage");
        messageListenerAdapter.addQueueOrTagToMethodName("lcTest","process");
        String[] strings = {"QueueName","lcTest"};
        container.addQueueNames(strings);
        container.setMessageListener(messageListenerAdapter);
    }
}
