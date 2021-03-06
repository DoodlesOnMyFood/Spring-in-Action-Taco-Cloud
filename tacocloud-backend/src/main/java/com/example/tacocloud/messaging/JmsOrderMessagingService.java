//package com.example.tacocloud.messaging;
//
//
//import com.example.tacocloud.Order;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//
//@Service
//public class JmsOrderMessagingService implements OrderMessagingService{
//    private JmsTemplate jms;
//
//    public JmsOrderMessagingService(JmsTemplate jms) {
//        this.jms = jms;
//    }
//
//    @Override
//    public void sendOrder(Order order) {
//        jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
//    }
//    private Message addOrderSource(Message message) throws JMSException {
//        message.setStringProperty("X_ORDER_SOURCE", "WEB");
//        return message;
//    }
//
//}
