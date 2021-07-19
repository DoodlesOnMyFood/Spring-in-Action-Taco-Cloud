package com.example.tacocloud.messaging.kitchen.messaging;

import com.example.tacocloud.Order;
import com.example.tacocloud.messaging.kitchen.KitchenUi;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    KitchenUi kitchenUi;

    public OrderListener(KitchenUi kitchenUi) {
        this.kitchenUi = kitchenUi;
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order){
        kitchenUi.displayOrder(order);
    }
}
