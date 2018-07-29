package com.mc.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {
	@Value("${spring.rabbitmq.username}")
	private String uname;
	@Value("${spring.rabbitmq.password}")
	private String upassword;
	@Value("${spring.rabbitmq.port}")
	private int port;
	@Value("${spring.rabbitmq.host}")
	private String uhost;
	
	
	@Bean
	public ConnectionFactory connectFactory() {
		CachingConnectionFactory connectionFactory=new CachingConnectionFactory();
	    connectionFactory.setUsername(uname);
	    connectionFactory.setPassword(upassword);
	    connectionFactory.setVirtualHost("/");
	    connectionFactory.setHost(uhost);
	   // connectionFactory.setPublisherConfirms(true);
	    return connectionFactory;
	}

	/**
	 * 创建admin，为了能自定义队列或交换机
	 * @return
	 */
	 @Bean
	 public RabbitAdmin rabbitAdmin(){
		 return new RabbitAdmin(connectFactory());
	 }  
	  
	 /** 
	  * 创建rabbitTemplate 消息模板类 
	  * prototype原型模式:每次获取Bean的时候会有一个新的实例 
	  *  因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 
	  * @return 
	  */  
	 @Bean
	 public RabbitTemplate rabbitTemplate(){
		 RabbitTemplate rabbitTemplate=new RabbitTemplate(connectFactory());
		 rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());//数据转换为json存入消息队列
		 //  rabbitTemplate.setReplyAddress(replyQueue().getName());  
		 //  rabbitTemplate.setReplyTimeout(100000000);  
		 //发布确认   
		 return rabbitTemplate;  
	 }  
}
