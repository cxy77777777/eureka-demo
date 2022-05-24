package com.test.serviceconsumer.controller;

import com.test.serviceconsumer.entity.Order;
import com.test.serviceconsumer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("getOrder")
    public List<Order> getOrder(){
        List<Order>list =  orderService.getList();
        return list;
    }
}
