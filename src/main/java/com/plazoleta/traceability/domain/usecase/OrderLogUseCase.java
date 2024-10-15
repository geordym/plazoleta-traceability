package com.plazoleta.traceability.domain.usecase;

import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
public class OrderLogUseCase implements IOrderLogServicePort {
    private final IOrderLogPersistencePort orderLogPersistencePort;

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



}
