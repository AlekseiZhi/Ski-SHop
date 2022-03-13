package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.Dto.UserForAuthDto;
import ru.skishop.service.AuthService;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String dynamicBuilderGeneric(@RequestBody UserForAuthDto userForAuthDto){
       return authService.login(userForAuthDto);
    }

    @PostMapping("/register")
    public void createNewUser(@RequestBody UserForAuthDto userForAuthDto) {
        System.out.println("работает контроллер на создание Юзера");
        authService.register(userForAuthDto);
    }
}