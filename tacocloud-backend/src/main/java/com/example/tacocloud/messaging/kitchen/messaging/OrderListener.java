package com.example.tacocloud.messaging.kitchen.messaging;

import com.example.tacocloud.Order;
import com.example.tacocloud.messaging.kitchen.KitchenUi;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    KitchenUi kitchenUi;

    public OrderListener(KitchenUi kitchenUi) {
        this.kitchenUi = kitchenUi;
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Order order){
        kitchenUi.displayOrder(order);
    }
}
