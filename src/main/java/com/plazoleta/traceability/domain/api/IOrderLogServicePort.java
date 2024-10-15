package com.plazoleta.traceability.domain.api;

import com.plazoleta.traceability.domain.model.OrderStatusLog;

public interface IOrderLogServicePort {


    void createLog(OrderStatusLog orderStatusLog);

}
