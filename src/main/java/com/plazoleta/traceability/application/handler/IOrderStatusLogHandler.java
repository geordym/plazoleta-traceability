package com.plazoleta.traceability.application.handler;

import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;

public interface IOrderStatusLogHandler {

    void createOrderStatusLog(CreateOrderStatusLogRequest createOrderStatusLogRequest);

}
