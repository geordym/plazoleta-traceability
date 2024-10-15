package com.plazoleta.traceability.infraestructure.out.mongo.adapter;

import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.infraestructure.out.mongo.entity.OrderStatusLogEntity;
import com.plazoleta.traceability.infraestructure.out.mongo.mapper.IOrderStatusLogEntityMapper;
import com.plazoleta.traceability.infraestructure.out.mongo.repository.IOrderStatusLogRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderLogMongoAdapter implements IOrderLogPersistencePort {

    private final IOrderStatusLogRepository orderStatusLogRepository;
    private final IOrderStatusLogEntityMapper orderStatusLogEntityMapper;

    @Override
    public OrderStatusLog saveOrderStatusLog(OrderStatusLog orderStatusLog) {
        OrderStatusLogEntity orderStatusLogEntity = orderStatusLogEntityMapper.toEntity(orderStatusLog);
        return orderStatusLogEntityMapper.toModel(orderStatusLogRepository.save(orderStatusLogEntity));
    }

    @Override
    public Optional<OrderStatusLog> findLastOrderStatusLogByOrderId(Long orderId) {
        List<OrderStatusLogEntity> orderStatusLogEntityList = orderStatusLogRepository.findLastOrderStatusLogByOrderId(orderId);
        if(orderStatusLogEntityList.isEmpty()){
            return Optional.empty();
        }
        OrderStatusLogEntity orderStatusLogFirst = orderStatusLogEntityList.get(0);
        return Optional.of(orderStatusLogEntityMapper.toModel(orderStatusLogFirst));
    }


}
