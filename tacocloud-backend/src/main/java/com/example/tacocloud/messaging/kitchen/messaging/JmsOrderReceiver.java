//package com.example.tacocloud.messaging.kitchen.messaging;
//
//import com.example.tacocloud.Order;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.jms.JMSException;
//
//
//@Component
//public class JmsOrderReceiver implements OrderReceiver {
//    private JmsTemplate template;
//
//    public JmsOrderReceiver(JmsTemplate template) {
//        this.template = template;
//    }
//
//    @Override
//    public Order receiveOrder() throws JMSException {
//        return (Order) template.receiveAndConvert("tacocloud.order.queue");
//    }
//}
