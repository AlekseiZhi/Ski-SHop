package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skishop.dto.RoleDto;
import ru.skishop.dto.UserDto;
import ru.skishop.dto.UserForAuthDto;
import ru.skishop.entity.User;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.mapper.UserMapper;
import ru.skishop.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
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

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.error("UserService: Not found User by email = {}", email);
            throw new NotFoundException("Not found User by email = " + email);
        }
        return user;
    }

    public UserDto findById(Long userId) {
        User user = userRepository.findUserById(userId);
        if (!userRepository.existsById(userId)) {
            log.info("UserService: Not found User by id = {}", userId);
            throw new NotFoundException("Not found User by id = " + userId);
        }
        return userMapper.toUserDto(user);
    }

    @Transactional
    public UserDto createNewUser(UserDto userDto) {
        return createOrUpdate(userDto);
    }

    @Transactional
    public User createNewUser(UserForAuthDto userForAuthDto) {
        User user = userMapper.toEntity(userForAuthDto);
        user.setRoles(List.of(roleService.getDefaultRole()));
        user.setPassword(passwordEncoder.encode(userForAuthDto.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserDto editUser(UserDto userDto) {
        if ((userRepository.findUserByEmail(userDto.getEmail())) == null) {
            log.error("UserService: Not found User by email = {}", userDto.getEmail());
            throw new NotFoundException("Not found User by email = " + userDto.getEmail());
        }
        return createOrUpdate(userDto);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("UserService: Not found user by id = {}", userId);
            throw new NotFoundException("Not found user by id = " + userId);
        }
        userRepository.deleteById(userId);
    }

    private UserDto createOrUpdate(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Long> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());
        user.setRoles(roleService.getRolesByIds(roleIds));
        return userMapper.toUserDto(userRepository.save(user));
    }
}