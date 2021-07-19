//package com.example.tacocloud.messaging.kitchen.messaging;
//
//import com.example.tacocloud.Order;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitOrderReceiver {
//    RabbitTemplate template;
//    MessageConverter converter;
//
//    public RabbitOrderReceiver(RabbitTemplate template, MessageConverter converter) {
//        this.template = template;
//        this.converter = converter;
//    }
//
//    public Order receiveOrder(){
//        return template.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<Order>(){});
//    }
//}
