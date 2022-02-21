package ru.skishop.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDto;
import ru.skishop.entities.User;
import ru.skishop.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User convertUserDTOToUser(UserDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roleService.getRolesByIds(userDTO.getListRoleId()));
        return user;
    }

    public UserDto convertUserToUserDTO(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getFullName());
        userDTO.setId(user.getId());
        return userDTO;
    }

    public UserDto createNewUser(UserDto userDTO) {
        User user = convertUserDTOToUser(userDTO);
        userRepository.save(user);
        return convertUserToUserDTO(userRepository.save(user));
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return convertUserToUserDTO(user);
    }

    public UserDto editUser(UserDto userDTO) {
        User user = convertUserDTOToUser(userDTO);
        return convertUserToUserDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}