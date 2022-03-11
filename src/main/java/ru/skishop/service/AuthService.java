package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDtoForAuth;
import ru.skishop.entities.User;
import ru.skishop.mappers.UserMapper;
import ru.skishop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createNewUser(UserDtoForAuth userDtoForAuth) {
        User user = userMapper.toUserAuth(userDtoForAuth);
        user.setPassword(passwordEncoder.encode(userDtoForAuth.getPassword()));
        userRepository.save(user);
    }
}