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


    //项目启动自动生成交换机和队列的配置在RabbitmqConfig里
    @Override
    public void run(String... args) throws Exception {
        SimpleMessageListenerContainer container = SpringUtil.getBean(SimpleMessageListenerContainer.class);//队列监听容器
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new Receive());
        messageListenerAdapter.addQueueOrTagToMethodName("QueueName","handleMessage");//将队列绑定到对应消息处理方法，这里由于QueueName有另外一个监听（消费者），所以每次只有一个监听会被执行，也就是只有一个消费者能消费到队列消息！！
        messageListenerAdapter.addQueueOrTagToMethodName("lcTest","process");
        String[] strings = {"QueueName","lcTest"};
        container.addQueueNames(strings);//监听队列集合
        container.setMessageListener(messageListenerAdapter);//设置队列监听器
    }
}
