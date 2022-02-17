package ru.skishop.service;

import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDTO;
import ru.skishop.entities.User;
import ru.skishop.repository.UserRepository;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setId(user.getId());
        return userDTO;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        User user = convertUserDTOToUser(userDTO);
        userRepository.saveAndFlush(user);
        return convertUserToUserDTO(userRepository.saveAndFlush(user));
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