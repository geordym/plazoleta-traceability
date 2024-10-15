package com.plazoleta.traceability.application.mapper;


import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.domain.model.OrderStatusLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderStatusRequestMapper {

    OrderStatusLog toModel(CreateOrderStatusLogRequest createOrderStatusLogRequest);

}
