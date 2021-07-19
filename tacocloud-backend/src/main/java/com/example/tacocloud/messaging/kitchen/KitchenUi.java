package com.example.tacocloud.messaging.kitchen;

import com.example.tacocloud.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KitchenUi {
    public void displayOrder(Order order){
        log.info("Order received : " + order);
    }
}
