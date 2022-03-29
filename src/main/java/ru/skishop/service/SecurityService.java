package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skishop.dto.UserInfoToken;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final JwtService jwtService;

    public void addToSecurityContext(String token) {
        UserInfoToken userInfoToken = jwtService.createTokenFromBearerToken(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfoToken.getId(), "", mapRolesToAuthorities(userInfoToken.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private List<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
    }
}
