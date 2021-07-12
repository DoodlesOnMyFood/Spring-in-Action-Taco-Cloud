package com.example.tacocloud.web;

import com.example.tacocloud.Order;
import com.example.tacocloud.User;
import com.example.tacocloud.data.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        System.out.println(model.toString());
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order, BindingResult results, SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user){
        if(results.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();
        log.info("Order submitted : " + order);
        return "redirect:/";
    }
}
