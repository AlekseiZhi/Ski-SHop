package ru.skishop.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.DTO.UserDtoForAuth;
import ru.skishop.service.AuthService;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
//    public static final long TOKEN_VALIDITY = 10 * 60 * 60;
//    private String jwtSecret = "123";

//    public String generateJwtToken(@RequestBody UserDtoForAuth userDtoForAuth) {
//
//       return Jwts.builder().setClaims(claims).setSubject(userDtoForAuth.getFullName())
//               .setIssuedAt(new Date(System.currentTimeMillis()))
//               .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
//               .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//   }

//    @PostMapping("/login")
//    public String dynamicBuilderGeneric(@RequestBody Map<String, Object> claims){
//
//        String jws =  Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//               .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
//               .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//
//        return jws;
//    }

    @PostMapping("/register")
    public void createUser(@RequestBody UserDtoForAuth userDtoForAuth) {
        System.out.println("работает контроллер на создание Юзера");
        authService.createNewUser(userDtoForAuth);
    }
}