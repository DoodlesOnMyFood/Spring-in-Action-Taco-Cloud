package com.example.tacocloud.messaging.kitchen.messaging;

import com.example.tacocloud.Order;
import com.example.tacocloud.messaging.kitchen.KitchenUi;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {
    KitchenUi kitchenUi;

    public OrderListener(KitchenUi kitchenUi) {
        this.kitchenUi = kitchenUi;
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void receiveOrder(Order order, ConsumerRecord<String, Order> record){
        log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
        kitchenUi.displayOrder(order);
    }
}
