package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDto;
import ru.skishop.entities.User;
import ru.skishop.mappers.UserMapper;
import ru.skishop.mappers.UserMapperImpl;
import ru.skishop.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public UserDto createNewUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roleService.getRolesByIds(userDto.getListRoleId()));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return userMapper.toUserDto(user);
    }

    public UserDto editUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roleService.getRolesByIds(userDto.getListRoleId()));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}