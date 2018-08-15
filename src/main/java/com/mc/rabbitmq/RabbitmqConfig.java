package com.mc.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 初始配置
 * @author lc
 */
@Component
public class RabbitmqConfig {
	@Value("${params.Rabbitmq.ExchangeName}")
	private String exchangeName;

	@Value("${params.Rabbitmq.QueueName}")
	private String queueName;

	private AmqpAdmin amqpAdmin;
	@Autowired
	public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	/**
	 *  项目启动即能创建的Exchange
	 *  可以创建各种类型的Exchange，父类都是 AbstractExchange
	 *  这里举例Topic类型
	 *  如果需要创建多个同类型可以用@Bean(name="beanName")，引用时用@Qualifier("beanName" )
	 */
	@Bean
	public TopicExchange exchange(){
		TopicExchange dataExchange = new TopicExchange(exchangeName,true,false);
		amqpAdmin.declareExchange(dataExchange);
		return dataExchange;
	}

	/**
	 * 项目创建就生成的Queue
	 * @return
	 */
	@Bean
	public Queue queue(){
		Queue queue = new Queue(queueName,true,false,false);
		amqpAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public Queue test(){
		Queue queue = new Queue("lcTest",true,false,false);
		amqpAdmin.declareQueue(queue);
		return queue;
	}
}
