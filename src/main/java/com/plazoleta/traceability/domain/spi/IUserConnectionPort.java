package com.plazoleta.traceability.domain.spi;

import com.plazoleta.traceability.domain.model.external.Employee;
import com.plazoleta.traceability.domain.model.external.User;

import java.util.Optional;

public interface IUserConnectionPort {

    Optional<User> findUserById(Long userId);
    Optional<Employee> findEmployeeByUserId(Long userId);


}
