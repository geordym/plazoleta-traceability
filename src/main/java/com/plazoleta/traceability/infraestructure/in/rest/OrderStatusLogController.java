package com.plazoleta.traceability.infraestructure.in.rest;


import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.application.handler.IOrderStatusLogHandler;
import com.plazoleta.traceability.domain.model.OrderRanking;
import com.plazoleta.traceability.domain.model.OrderWithLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-log")
@RequiredArgsConstructor
public class OrderStatusLogController {

    private final IOrderStatusLogHandler orderStatusLogHandler;

    @GetMapping("/ranking/{restaurantId}")
    public ResponseEntity<OrderRanking> getOrderRankingOfRestaurant(@PathVariable Long restaurantId){
        OrderRanking orderRanking = orderStatusLogHandler.getOrderRankingOfRestaurant(restaurantId);
        return new ResponseEntity<>(orderRanking, HttpStatus.OK);
    }


    @GetMapping("/order/customer")
    public ResponseEntity<List<OrderWithLog>> getAllOrderLogByCustomerId(){
        List<OrderWithLog> orderWithLog = orderStatusLogHandler.getAllOrderLogByCustomer();
        return new ResponseEntity<>(orderWithLog, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderWithLog> getOrderLogByOrderId(@PathVariable Long orderId){
        OrderWithLog orderWithLog = orderStatusLogHandler.getOrderLogByOrderId(orderId);
        return new ResponseEntity<>(orderWithLog, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createOrderStatusLog(@RequestBody CreateOrderStatusLogRequest createOrderStatusLogRequest){
        orderStatusLogHandler.createOrderStatusLog(createOrderStatusLogRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
