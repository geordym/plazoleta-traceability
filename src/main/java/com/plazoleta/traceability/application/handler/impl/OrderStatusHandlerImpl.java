package com.plazoleta.traceability.application.handler.impl;

import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.application.handler.IOrderStatusLogHandler;
import com.plazoleta.traceability.application.mapper.IOrderStatusRequestMapper;
import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import com.plazoleta.traceability.domain.model.OrderRanking;
import com.plazoleta.traceability.domain.model.OrderWithLog;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderStatusHandlerImpl implements IOrderStatusLogHandler {

    private final IOrderLogServicePort orderLogServicePort;
    private final IOrderStatusRequestMapper orderStatusRequestMapper;

    @Override
    public void createOrderStatusLog(CreateOrderStatusLogRequest createOrderStatusLogRequest) {
        orderLogServicePort.createLog(orderStatusRequestMapper.toModel(createOrderStatusLogRequest));
    }

    @Override
    public OrderWithLog getOrderLogByOrderId(Long orderId) {
        return orderLogServicePort.getOrderLogByOrderId(orderId);
    }

    @Override
    public List<OrderWithLog> getAllOrderLogByCustomer() {
        List<OrderWithLog> order = orderLogServicePort.getAllOrderLogByCustomer();
        return order;
    }

    @Override
    public OrderRanking getOrderRankingOfRestaurant(Long restaurantId) {
        return orderLogServicePort.getOrderRankingOfRestaurant(restaurantId);
    }

}
