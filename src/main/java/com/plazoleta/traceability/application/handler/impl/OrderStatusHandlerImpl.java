package com.plazoleta.traceability.application.handler.impl;

import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.application.handler.IOrderStatusLogHandler;
import com.plazoleta.traceability.application.mapper.IOrderStatusRequestMapper;
import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderStatusHandlerImpl implements IOrderStatusLogHandler {

    private final IOrderLogServicePort orderLogServicePort;
    private final IOrderStatusRequestMapper orderStatusRequestMapper;

    @Override
    public void createOrderStatusLog(CreateOrderStatusLogRequest createOrderStatusLogRequest) {
        orderLogServicePort.createLog(orderStatusRequestMapper.toModel(createOrderStatusLogRequest));
    }

}
