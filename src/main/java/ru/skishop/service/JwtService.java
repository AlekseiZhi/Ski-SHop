package ru.skishop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserInfoToken;
import ru.skishop.entities.Role;
import ru.skishop.entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    Date now = new Date();
    Date validity = new Date(now.getTime() + 36000000);

    public TokenWrapperDto createJwt(User user) {
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        Claims claims = Jwts.claims().setExpiration(new Date());
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", roles);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        System.out.println(jwt);
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