package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.Dto.UserForAuthDto;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public void register(UserForAuthDto userForAuthDto) {
        userService.register(userForAuthDto);
    }

    public String login(UserForAuthDto userForAuthDto) {
        return jwtService.createJWT(userService.findUserByEmail(userForAuthDto.getEmail()));
    }
}