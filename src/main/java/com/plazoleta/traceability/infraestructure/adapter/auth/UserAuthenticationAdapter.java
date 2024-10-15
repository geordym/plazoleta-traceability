package com.plazoleta.traceability.infraestructure.adapter.auth;

import com.plazoleta.traceability.domain.model.external.Role;
import com.plazoleta.traceability.domain.model.external.User;
import com.plazoleta.traceability.domain.spi.IUserAuthenticationPort;
import com.plazoleta.traceability.domain.spi.IUserConnectionPort;
import com.plazoleta.traceability.infraestructure.configuration.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class UserAuthenticationAdapter implements IUserAuthenticationPort {

    private final IUserConnectionPort userConnectionPort;

    @Override
    public User getAuthenticatedUser() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userConnectionPort.findUserById(Long.valueOf(customUserDetails.getUserId())).orElseThrow();
    }

    @Override
    public Long getAuthenticatedUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(customUserDetails.getUserId());
    }

    @Override
    public Role getAuthenticatedUserRole() {
        return null; //TODO: If you need that later, use that
    }



}
