package ru.skishop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserInfoToken;
import ru.skishop.entity.Role;
import ru.skishop.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final int BEARER_HEADER_LENGTH = 7;

    public TokenWrapperDto createJwt(User user) {
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", roles);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenWrapperDto(jwt);
    }

    public UserInfoToken createTokenFromBearerToken(String token) {
        UserInfoToken userInfoToken = new UserInfoToken();
        Claims payload = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        userInfoToken.setId(payload.get("id", Long.class));
        userInfoToken.setEmail(payload.get("email", String.class));
        userInfoToken.setRoles(payload.get("roles", List.class));

        return userInfoToken;
    }
}