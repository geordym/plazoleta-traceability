package com.plazoleta.traceability.infraestructure.in.rest;


import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.application.handler.IOrderStatusLogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-log")
@RequiredArgsConstructor
public class OrderStatusLogController {

    private final IOrderStatusLogHandler orderStatusLogHandler;

    @PostMapping
    public ResponseEntity<Void> createOrderStatusLog(@RequestBody CreateOrderStatusLogRequest createOrderStatusLogRequest){
        orderStatusLogHandler.createOrderStatusLog(createOrderStatusLogRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
