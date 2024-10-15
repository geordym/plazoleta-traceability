package com.plazoleta.traceability.domain.usecase;

import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.model.OrderWithLog;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class OrderLogUseCase implements IOrderLogServicePort {
    private final IOrderLogPersistencePort orderLogPersistencePort;
    private final IUserAuthenticationPort userAuthenticationPort;

    @Override
    public void createLog(OrderStatusLog orderStatusLog) {
        String lastOrderStatus = "";
        Optional<OrderStatusLog> lastOrderStatusLog = orderLogPersistencePort.findLastOrderStatusLogByOrderId(orderStatusLog.getOrderId());
        if(lastOrderStatusLog.isPresent()){
            OrderStatusLog orderStatusLog1 = lastOrderStatusLog.get();
            lastOrderStatus = orderStatusLog1.getStatus();
        }

        orderStatusLog.setPreviousStatus(lastOrderStatus);
        orderStatusLog.setUpdatedAt(LocalDateTime.now());
        orderLogPersistencePort.saveOrderStatusLog(orderStatusLog);
    }


    @Override
    public OrderWithLog getOrderLogByOrderId(Long orderId) {
        List<OrderStatusLog> orderStatusLogList = orderLogPersistencePort.findAllOrderStatusLogByOrderId(orderId);
        OrderWithLog orderWithLog = new OrderWithLog();
        orderWithLog.setOrderId(orderId);
        orderWithLog.setLogs(orderStatusLogList);
        return orderWithLog;
    }

    @Override
    public List<OrderWithLog> getAllOrderLogByCustomer() {
        Long customerId = userAuthenticationPort.getAuthenticatedUserId();
        List<OrderStatusLog> orderStatusLogList = orderLogPersistencePort.findAllOrderStatusLogByCustomerId(customerId);

        Map<Long, List<OrderStatusLog>> logsByOrderId = orderStatusLogList.stream()
                .collect(Collectors.groupingBy(OrderStatusLog::getOrderId));

        return logsByOrderId.entrySet().stream()
                .map(entry -> {
                    OrderWithLog orderWithLog = new OrderWithLog();
                    orderWithLog.setOrderId(entry.getKey());
                    orderWithLog.setLogs(entry.getValue());
                    return orderWithLog;
                })
                .toList();
    }


}
