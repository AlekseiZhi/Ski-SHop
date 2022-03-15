package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public TokenWrapperDto register(UserForAuthDto userForAuthDto) {
        TokenWrapperDto tokenWrapperDto = new TokenWrapperDto();
        tokenWrapperDto.setAccessToken(jwtService.createJwt(userService.createNewUser(userForAuthDto)));
        return tokenWrapperDto;
    }

    public TokenWrapperDto login(UserForAuthDto userForAuthDto) {
        TokenWrapperDto tokenWrapperDto = new TokenWrapperDto();
        tokenWrapperDto.setAccessToken(jwtService.createJwt(userService.findUserByEmail(userForAuthDto.getEmail())));
        return tokenWrapperDto;
    }
}