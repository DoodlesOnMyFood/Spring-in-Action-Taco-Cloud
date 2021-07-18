package com.example.tacocloud.api;

import com.example.tacocloud.Order;
import com.example.tacocloud.data.OrderRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/orders", produces = "application/json")
@CrossOrigin(origins = "*")
class OrderController{

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Iterable<Order> allOrders(){
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }


    @PutMapping(consumes = "application/json", path="/{id}")
    public Order putOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }

    @PatchMapping(consumes = "application/json", path="/{id}")
    public Order patchOrder(@RequestBody Order patch, @PathVariable Long id){
        Order order = orderRepository.findById(id).get();
        if(patch.getDeliveryName() != null)
            order.setDeliveryName(patch.getDeliveryName());
        if(patch.getState() != null)
            order.setState(patch.getState());
        if(patch.getStreet() != null)
            order.setStreet(patch.getStreet());
        if(patch.getCity() != null)
            order.setCity(patch.getCity());
        if(patch.getCcNumber() != null)
            order.setCcNumber(patch.getCcNumber());
        if(patch.getCcExpiration() != null)
            order.setCcExpiration(patch.getCcExpiration());
        if(patch.getCcCVV() != null)
            order.setCcCVV(patch.getCcCVV());

        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        try {
            orderRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e){}
    }

}
