package ru.skishop.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skishop.service.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class UserAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final SecurityService securityService;
    private static final int BEARER_HEADER_LENGTH = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader("Authorization");

        if (header == null) {
            log.info("UserAuthFilter: Header is empty");
            throw new RuntimeException();
        }

        if (header.length() < BEARER_HEADER_LENGTH) {
            log.info("UserAuthFilter: Token header is not valid ");
            throw new RuntimeException();
        }

        String token = header.substring(BEARER_HEADER_LENGTH);
        securityService.addToSecurityContext(token);
        chain.doFilter(request, response);
    }
}