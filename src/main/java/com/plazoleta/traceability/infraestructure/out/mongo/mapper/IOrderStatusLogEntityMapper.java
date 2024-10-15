package com.plazoleta.traceability.infraestructure.out.mongo.mapper;

import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.infraestructure.out.mongo.entity.OrderStatusLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderStatusLogEntityMapper {

    OrderStatusLogEntity toEntity(OrderStatusLog orderStatusLog);
    OrderStatusLog toModel(OrderStatusLogEntity orderStatusLogEntity);

}
