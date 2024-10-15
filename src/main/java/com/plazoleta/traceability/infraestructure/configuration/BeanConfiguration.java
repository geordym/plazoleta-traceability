package com.plazoleta.traceability.infraestructure.configuration;

import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import com.plazoleta.traceability.domain.spi.IUserConnectionPort;
import com.plazoleta.traceability.domain.spi.security.ITokenProviderPort;
import com.plazoleta.traceability.infraestructure.adapter.auth.UserAuthenticationAdapter;
import com.plazoleta.traceability.infraestructure.adapter.security.JwtIOTokenAdapter;
import com.plazoleta.traceability.infraestructure.out.feign.adapter.UserConnectionFeignAdapter;
import com.plazoleta.traceability.infraestructure.out.feign.client.IUserConnectionFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserConnectionFeignClient userConnectionFeignClient;

    @Bean
    public IUserAuthenticationPort userAuthenticationPort(){
        return new UserAuthenticationAdapter(userConnectionPort());
    }

    @Bean
    public IUserConnectionPort userConnectionPort(){
        return new UserConnectionFeignAdapter(userConnectionFeignClient);
    }

    @Bean
    public ITokenProviderPort tokenProviderPort(){
        return new JwtIOTokenAdapter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
