package com.plazoleta.traceability.domain.usecase;


import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.model.OrderWithLog;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderLogUseCaseTest {

    @Mock
    private IUserAuthenticationPort userAuthenticationPort;

    @Mock
    private IOrderLogPersistencePort orderLogPersistencePort;

    private OrderLogUseCase orderLogUseCase;

    @BeforeEach
    void setup(){
        orderLogUseCase = new OrderLogUseCase(orderLogPersistencePort, userAuthenticationPort);
    }

    @Test
    void testCreateLog_WhenLastLogExists() {
        // Arrange
        Long orderId = 1L;
        OrderStatusLog newLog = new OrderStatusLog();
        newLog.setOrderId(orderId);
        newLog.setStatus("PENDING");

        OrderStatusLog lastLog = new OrderStatusLog();
        lastLog.setStatus("CREATED");

        when(orderLogPersistencePort.findLastOrderStatusLogByOrderId(orderId)).thenReturn(Optional.of(lastLog));

        orderLogUseCase.createLog(newLog);

        assertEquals("CREATED", newLog.getPreviousStatus());
        assertNotNull(newLog.getUpdatedAt());
        verify(orderLogPersistencePort).saveOrderStatusLog(newLog);
    }

    @Test
    void testCreateLog_WhenLastLogDoesNotExist() {
        Long orderId = 1L;
        OrderStatusLog newLog = new OrderStatusLog();
        newLog.setOrderId(orderId);
        newLog.setStatus("PENDING");

        when(orderLogPersistencePort.findLastOrderStatusLogByOrderId(orderId)).thenReturn(Optional.empty());

        orderLogUseCase.createLog(newLog);

        assertEquals("", newLog.getPreviousStatus());
        assertNotNull(newLog.getUpdatedAt());
        verify(orderLogPersistencePort).saveOrderStatusLog(newLog);
    }

    @Test
    void testGetOrderLogByOrderId() {
        // Arrange
        Long orderId = 1L;
        OrderStatusLog log = new OrderStatusLog();
        log.setOrderId(orderId);
        log.setStatus("PENDING");

        when(orderLogPersistencePort.findAllOrderStatusLogByOrderId(orderId)).thenReturn(List.of(log));

        OrderWithLog result = orderLogUseCase.getOrderLogByOrderId(orderId);

        assertEquals(orderId, result.getOrderId());
        assertEquals(1, result.getLogs().size());
        assertEquals("PENDING", result.getLogs().get(0).getStatus());
    }

    @Test
    void testGetAllOrderLogByCustomer() {
        // Arrange
        Long customerId = 1L;
        when(userAuthenticationPort.getAuthenticatedUserId()).thenReturn(customerId);

        OrderStatusLog log1 = new OrderStatusLog();
        log1.setOrderId(1L);
        log1.setStatus("PENDING");

        OrderStatusLog log2 = new OrderStatusLog();
        log2.setOrderId(1L);
        log2.setStatus("COMPLETED");

        when(orderLogPersistencePort.findAllOrderStatusLogByCustomerId(customerId))
                .thenReturn(List.of(log1, log2));

        List<OrderWithLog> result = orderLogUseCase.getAllOrderLogByCustomer();

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getLogs().size());
        assertEquals(1L, result.get(0).getOrderId());
    }

}
