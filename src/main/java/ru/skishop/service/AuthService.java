package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;
import ru.skishop.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public TokenWrapperDto register(UserForAuthDto userForAuthDto) {
        User user = userService.createNewUser(userForAuthDto);
        return jwtService.createJwt(user);
    }

    public TokenWrapperDto login(UserForAuthDto userForAuthDto) {
        User user = userService.findUserByEmail(userForAuthDto.getEmail());
        return jwtService.createJwt(user);
    }
}