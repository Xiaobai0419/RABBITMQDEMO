package com.mc.controller;

import com.mc.rabbitmq.RabbitSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "Test")
public class TestController {
    private RabbitSendUtil rabbitSendUtil;
    @Autowired
    public void setRabbitSendUtil(RabbitSendUtil rabbitSendUtil) {
        this.rabbitSendUtil = rabbitSendUtil;
    }

    @GetMapping(value = "test")
    public String test(){
        return "success";
    }

    @GetMapping(value = "/send/lcTest/{message}")
    public void sendTest1(@PathVariable String message){
        rabbitSendUtil.sendToQueue("lcTest",message);
    }

    @GetMapping(value = "/send/QueueName/{message}")
    public void sendTest2(@PathVariable String message){
        rabbitSendUtil.sendToQueue("QueueName",message);
    }
}
