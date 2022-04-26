package ru.skishop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;
import ru.skishop.service.AuthService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Api(tags = "Auth Controller")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login on Ski Shop"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public TokenWrapperDto login(@Valid @RequestBody UserForAuthDto userForAuthDto) {
        return authService.login(userForAuthDto);
    }

    @PostMapping("/register")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Register on Ski Shop"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public TokenWrapperDto createNewUser(@Valid @RequestBody UserForAuthDto userForAuthDto) {
        return authService.register(userForAuthDto);
    }
}