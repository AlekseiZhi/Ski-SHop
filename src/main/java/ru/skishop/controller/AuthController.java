package ru.skishop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;
import ru.skishop.service.AuthService;

@RequiredArgsConstructor
@RestController
@Api(tags = "Auth Controller")
//@Tag(name = "AuthController", description = "Operations for authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenWrapperDto dynamicBuilderGeneric(@RequestBody UserForAuthDto userForAuthDto) {
        return authService.login(userForAuthDto);
    }

    @PostMapping("/register")
    public TokenWrapperDto createNewUser(@RequestBody UserForAuthDto userForAuthDto) {
        return authService.register(userForAuthDto);
    }
}