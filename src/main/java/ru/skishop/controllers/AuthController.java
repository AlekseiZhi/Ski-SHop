package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;
import ru.skishop.service.AuthService;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenWrapperDto dynamicBuilderGeneric(@RequestBody UserForAuthDto userForAuthDto) {
        System.out.println("login controller");
        return authService.login(userForAuthDto);
    }

    @PostMapping("/register")
    public TokenWrapperDto createNewUser(@RequestBody UserForAuthDto userForAuthDto) {
        return authService.register(userForAuthDto);
    }
}