package com.example.tacocloud.messaging;

import com.example.tacocloud.Order;

public interface OrderMessagingService {
    public void sendOrder(Order order);
}
