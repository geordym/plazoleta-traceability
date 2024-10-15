package com.plazoleta.traceability.infraestructure.out.feign.adapter;

import com.plazoleta.traceability.domain.model.external.Employee;
import com.plazoleta.traceability.domain.model.external.User;
import com.plazoleta.traceability.domain.spi.IUserConnectionPort;
import com.plazoleta.traceability.infraestructure.out.feign.client.IUserConnectionFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserConnectionFeignAdapter implements IUserConnectionPort {

    private final IUserConnectionFeignClient userConnectionFeignClient;

    @Override
    public Optional<User> findUserById(Long userId) {
        try {
            User user = userConnectionFeignClient.findClientById(userId);
            return Optional.ofNullable(user);
        } catch (FeignException.NotFound ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Employee> findEmployeeByUserId(Long userId) {
        try {
            Employee employee = userConnectionFeignClient.findEmployeeByIdUserId(userId);
            return Optional.ofNullable(employee);
        } catch (FeignException.NotFound ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }


}
