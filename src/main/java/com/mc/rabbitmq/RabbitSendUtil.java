package com.mc.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 对AmqpAdmin的接口的方法的简单解释与自我学习使用
 */
@Component
public class RabbitSendUtil {

	private final static Logger logger = LoggerFactory.getLogger(RabbitSendUtil.class);

	private final AmqpAdmin amqpAdmin;

	private final AmqpTemplate amqpTemplate;

	/** 自定义的Exchange */
	private final TopicExchange topicExchange;

	@Autowired
	public RabbitSendUtil(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, TopicExchange topicExchange){
		this.amqpAdmin = amqpAdmin;
		this.amqpTemplate = amqpTemplate;
		this.topicExchange = topicExchange;
	}


	/**
	 * 转换Message对象
	 * @param messageType 返回消息类型 MessageProperties类中常量
	 * @param msg
	 * @return
	 */
	public Message getMessage(String messageType,Object msg){
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(messageType);
		Message message = new Message(msg.toString().getBytes(),messageProperties);
		return message;
	}

	/**
	 * 有绑定Key的Exchange发送
	 * @param routingKey
	 * @param msg
	 */
	public void sendMessageToExchange(String routingKey, Object msg){
		Message message = getMessage(MessageProperties.CONTENT_TYPE_JSON,msg);
		amqpTemplate.send(topicExchange.getName(),routingKey,message);
	}

	/**
	 * 没有绑定KEY的Exchange发送
	 * @param exchange
	 * @param msg
	 */
	public void sendMessageToExchange( AbstractExchange exchange, String msg){
		addExchange(exchange);
		logger.info("RabbitMQ send "+exchange.getName()+"->"+msg);
		amqpTemplate.convertAndSend(topicExchange.getName(),msg);
	}

	/**
	 * 给queue发送消息
	 * @param queueName
	 * @param msg
	 */
	public void sendToQueue(String queueName,String msg){
		Queue queue = new Queue(queueName);
		addQueue(queue);
		Binding binding = BindingBuilder.bind(queue).to(DirectExchange.DEFAULT).withQueueName();
		amqpAdmin.declareBinding(binding);
		Message message = new Message(msg.getBytes(),new MessageProperties());
		amqpTemplate.convertAndSend(DirectExchange.DEFAULT.getName(),queueName,message);
	}
	/**
	 * 创建Exchange
	 * @param exchange
	 */
	public void addExchange(AbstractExchange exchange){
		amqpAdmin.declareExchange(exchange);
	}

	/**
	 * 删除一个Exchange
	 * @param exchangeName
	 */
	public boolean deleteExchange(String exchangeName){
		return amqpAdmin.deleteExchange(exchangeName);
	}


	/**
	 * Declare a queue whose name is automatically named. It is created with exclusive = true, autoDelete=true, and
	 * durable = false.
	 * @return Queue
	 */
	public Queue addQueue(){
		return amqpAdmin.declareQueue();
	}

	/**
	 * 创建一个指定的Queue
	 * @param queue
	 * @return queueName
	 */
	public String addQueue(Queue queue){
		return amqpAdmin.declareQueue(queue);
	}

	/**
	 * Delete a queue.
	 * @param queueName the name of the queue.
	 * @param unused true if the queue should be deleted only if not in use.
	 * @param empty true if the queue should be deleted only if empty.
	 */
	public void deleteQueue(String queueName, boolean unused, boolean empty){
		amqpAdmin.deleteQueue(queueName,unused,empty);
	}

	/**
	 * 删除一个queue
	 * @return true if the queue existed and was deleted.
	 * @param queueName
	 */
	public boolean deleteQueue(String queueName){
	  	return amqpAdmin.deleteQueue(queueName);
	}

	/**
	 * 绑定一个队列到一个匹配型交换器使用一个routingKey
	 * @param queue
	 * @param exchange
	 * @param routingKey
	 */
	public void addBinding(Queue queue ,TopicExchange exchange,String routingKey){
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
		amqpAdmin.declareBinding(binding);
	}

	/**
	 * 去掉一个binding
	 * @param binding
	 */
	public void removeBinding(Binding binding){
		amqpAdmin.removeBinding(binding);
	}

}
