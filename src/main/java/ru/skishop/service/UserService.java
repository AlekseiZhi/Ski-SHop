package ru.skishop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDTO;
import ru.skishop.entities.User;
import ru.skishop.repository.RoleRepository;
import ru.skishop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roleService.getRolesByIds(userDTO.getListRoleId()));
        return user;
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getFullName());
        userDTO.setId(user.getId());
        return userDTO;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        User user = convertUserDTOToUser(userDTO);
        userRepository.save(user);
        return convertUserToUserDTO(userRepository.save(user));
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException();
        });
        return convertUserToUserDTO(user);
    }

    public UserDTO editUser(UserDTO userDTO) {
        User user = convertUserDTOToUser(userDTO);
        return convertUserToUserDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}