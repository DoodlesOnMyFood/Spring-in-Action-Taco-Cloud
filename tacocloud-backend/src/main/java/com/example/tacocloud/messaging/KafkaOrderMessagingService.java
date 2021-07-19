package com.example.tacocloud.messaging;

import com.example.tacocloud.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService{

    private KafkaTemplate<String, Order> template;

    public KafkaOrderMessagingService(KafkaTemplate<String, Order> template) {
        this.template = template;
    }

    @Override
    public void sendOrder(Order order) {
        template.sendDefault(order);
    }
}
