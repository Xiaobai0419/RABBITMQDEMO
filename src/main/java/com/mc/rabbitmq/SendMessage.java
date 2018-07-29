package com.mc.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessage {

	private final static Logger logger = LoggerFactory.getLogger(SendMessage.class);

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

	/**
	 * 有绑定Key的Exchange发送
	 * @param routingKey
	 * @param exchange
	 * @param msg
	 */
	public void sendMessage(String routingKey, AbstractExchange exchange, String msg){
		rabbitAdmin.declareExchange(exchange);
		rabbitTemplate.setExchange(exchange.getName());
		logger.info("RabbitMQ send "+exchange.getName()+"->"+msg);
		rabbitTemplate.send(routingKey,new Message(msg.getBytes(), new MessageProperties()));
	}

	/**
	 * 没有绑定KEY的Exchange发送
	 * @param exchange
	 * @param msg
	 */
	public void sendMessage( AbstractExchange exchange, String msg){
		rabbitAdmin.declareExchange(exchange);
		rabbitTemplate.setExchange(exchange.getName());
		logger.info("RabbitMQ send "+exchange.getName()+"->"+msg);
		rabbitTemplate.send(new Message(msg.getBytes(), new MessageProperties()));
	}
	    
	
}
