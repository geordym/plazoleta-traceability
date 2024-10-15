package com.plazoleta.traceability.infraestructure.out.mongo.repository;

import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.infraestructure.out.mongo.entity.OrderStatusLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderStatusLogRepository extends MongoRepository<OrderStatusLogEntity, String> {

    List<OrderStatusLogEntity> findByOrderId(Long orderId);

    List<OrderStatusLogEntity> findByCustomerId(Long customerId);

    List<OrderStatusLogEntity> findByEmployeeId(Long employeeId);

    @Query(value = "{ 'orderId': ?0 }", sort = "{ 'updatedAt': -1 }")
    List<OrderStatusLogEntity> findLastOrderStatusLogByOrderId(Long orderId);

}