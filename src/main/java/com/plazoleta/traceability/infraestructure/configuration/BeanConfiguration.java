package com.plazoleta.traceability.infraestructure.configuration;

import com.plazoleta.traceability.domain.spi.security.ITokenProviderPort;
import com.plazoleta.traceability.infraestructure.adapter.security.JwtIOTokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public ITokenProviderPort tokenProviderPort(){
        return new JwtIOTokenAdapter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
