package ru.skishop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.entities.Role;
import ru.skishop.entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public TokenWrapperDto createJwt(User user) {
        TokenWrapperDto tokenWrapperDto = new TokenWrapperDto();
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        Claims claims = Jwts.claims().setExpiration(new Date());
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", roles);
        tokenWrapperDto.setAccessToken(Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact());
        return tokenWrapperDto;
    }
}