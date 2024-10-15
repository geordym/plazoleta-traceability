package com.plazoleta.traceability.infraestructure.out.feign.client;


import com.plazoleta.traceability.domain.model.external.Employee;
import com.plazoleta.traceability.domain.model.external.User;
import com.plazoleta.traceability.infraestructure.out.feign.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-API", url = "${external.user.api.base-url}", configuration = FeignClientConfig.class)
public interface IUserConnectionFeignClient {

    @GetMapping(value = "/api/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    User findClientById(@PathVariable Long userId);


    @GetMapping(value = "/api/users/employee/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Employee findEmployeeByIdUserId(@PathVariable Long userId);

}
