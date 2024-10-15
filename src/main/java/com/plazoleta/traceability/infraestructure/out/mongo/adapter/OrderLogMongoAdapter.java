package com.plazoleta.traceability.infraestructure.out.mongo.adapter;

import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.infraestructure.out.mongo.entity.OrderStatusLogEntity;
import com.plazoleta.traceability.infraestructure.out.mongo.mapper.IOrderStatusLogEntityMapper;
import com.plazoleta.traceability.infraestructure.out.mongo.repository.IOrderStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

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

    @Override
    public List<OrderStatusLog> findAllOrderStatusLogByOrderId(Long orderId) {
        boolean ascending = true;
        Sort sort = ascending ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending();
        List<OrderStatusLogEntity> orderStatusLogEntityList = orderStatusLogRepository.findAllByOrderId(orderId, sort);
        List<OrderStatusLog> orderStatusLogs = orderStatusLogEntityList.stream().map(orderStatusLogEntityMapper::toModel).toList();
        return orderStatusLogs;
    }

    @Override
    public List<OrderStatusLog> findAllOrderStatusLogByCustomerId(Long customerId) {
        boolean ascending = true;
        Sort sort = ascending ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending();
        List<OrderStatusLogEntity> orderStatusLogEntityList = orderStatusLogRepository.findAllByCustomerId(customerId, sort);
        List<OrderStatusLog> orderStatusLogs = orderStatusLogEntityList.stream().map(orderStatusLogEntityMapper::toModel).toList();
        return orderStatusLogs;
    }


}
