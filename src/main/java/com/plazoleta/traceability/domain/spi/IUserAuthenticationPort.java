package com.plazoleta.traceability.domain.spi;


import com.plazoleta.traceability.domain.model.external.Role;
import com.plazoleta.traceability.domain.model.external.User;

public interface IUserAuthenticationPort {
    User getAuthenticatedUser();
    Long getAuthenticatedUserId();
    Role getAuthenticatedUserRole();
}
