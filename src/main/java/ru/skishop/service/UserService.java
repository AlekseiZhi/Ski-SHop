package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.dto.RoleDto;
import ru.skishop.dto.UserDto;
import ru.skishop.dto.UserForAuthDto;
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

    public List<UserDto> findAllUsers() {
        return userRepository.findAllUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto createNewUser(UserDto userDto) {
        return createOrUpdate(userDto);
    }

    public User createNewUser(UserForAuthDto userForAuthDto) {
        User user = userMapper.toEntity(userForAuthDto);
        user.setRoles(List.of(roleService.getDefaultRole()));
        user.setPassword(passwordEncoder.encode(userForAuthDto.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return userMapper.toUserDto(user);
    }

    public UserDto editUser(UserDto userDto) {
        return createOrUpdate(userDto);
    }

    private UserDto createOrUpdate(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        user.setRoles(roleService.getRolesByIds(roleIds));
        return userMapper.toUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}