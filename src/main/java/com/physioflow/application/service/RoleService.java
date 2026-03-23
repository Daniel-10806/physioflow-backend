package com.physioflow.application.service;

import com.physioflow.domain.model.enumtype.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public boolean hasRole(Role role) {

        var auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null)
            return false;

        return auth
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals(role.name()));
    }
}