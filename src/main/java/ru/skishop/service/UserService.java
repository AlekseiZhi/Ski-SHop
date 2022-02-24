package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.DTO.RoleDto;
import ru.skishop.DTO.UserDto;
import ru.skishop.entities.User;
import ru.skishop.mappers.UserMapper;
import ru.skishop.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserDto createNewUser(UserDto userDto) {
        return createUserWithParams(userDto);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return userMapper.toUserDto(user);
    }

    public UserDto editUser(UserDto userDto) {
        return createUserWithParams(userDto);
    }

    private UserDto createUserWithParams(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        user.setRoles(roleService.getRolesByIds(roleIds));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}