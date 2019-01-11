package com.mc;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author lc
 * @date  2018/5/7
 */
@SpringBootApplication
public class Application {

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
        return new SimpleMessageListenerContainer(connectionFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}//TODO Exchange和Queue绑定，向Exchange发送广播消息，动态创建Queue接收更多消息（不能是广播，是分流总量消息，RabbitMQ需要生产者发送到新的Queue,考虑Kafka同一个消费者组动态添加消费者--消费端缓压，同一个Topic动态增加Partition--服务端扩容）