package com.plazoleta.traceability.infraestructure.configuration;


import com.plazoleta.traceability.application.handler.IOrderStatusLogHandler;
import com.plazoleta.traceability.application.handler.impl.OrderStatusHandlerImpl;
import com.plazoleta.traceability.application.mapper.IOrderStatusRequestMapper;
import com.plazoleta.traceability.domain.api.IOrderLogServicePort;
import com.plazoleta.traceability.domain.spi.IOrderLogPersistencePort;
import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import com.plazoleta.traceability.domain.usecase.OrderLogUseCase;
import com.plazoleta.traceability.infraestructure.out.mongo.adapter.OrderLogMongoAdapter;
import com.plazoleta.traceability.infraestructure.out.mongo.mapper.IOrderStatusLogEntityMapper;
import com.plazoleta.traceability.infraestructure.out.mongo.repository.IOrderStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderStatusLogBeanConfiguration {

    private final IOrderStatusRequestMapper orderStatusRequestMapper;
    private final IOrderStatusLogEntityMapper orderStatusLogEntityMapper;
    private final IOrderStatusLogRepository orderStatusLogRepository;
    private final IUserAuthenticationPort userAuthenticationPort;

    @Bean
    public IOrderStatusLogHandler orderStatusLogHandler(){
        return new OrderStatusHandlerImpl(orderLogServicePort(), orderStatusRequestMapper);
    }

    @Bean
    public IOrderLogServicePort orderLogServicePort(){
        return new OrderLogUseCase(orderLogPersistencePort(), userAuthenticationPort);
    }

    @Bean
    public IOrderLogPersistencePort orderLogPersistencePort(){
        return new OrderLogMongoAdapter(orderStatusLogRepository, orderStatusLogEntityMapper);
    }

}
