package sas.service.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sas.data.models.Product;
import sas.data.models.Refrigerator;
import sas.service.services.AuthenticatedUserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {
    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public List<String> getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
