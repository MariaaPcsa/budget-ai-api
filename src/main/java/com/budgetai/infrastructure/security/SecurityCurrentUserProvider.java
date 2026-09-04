package com.budgetai.infrastructure.security;

import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityCurrentUserProvider implements CurrentUserProvider {

    @Override
    public UUID currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
            throw new ResourceNotFoundException("Usuário autenticado não encontrado");
        }

        return user.id();
    }
}