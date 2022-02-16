package ru.skishop.service;

import org.springframework.stereotype.Service;
import ru.skishop.DTO.UserDTO;
import ru.skishop.entities.User;
import ru.skishop.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setId(userDTO.getId());
        return userDTO;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        User user = convertUserDTOToUser(userDTO);
       return convertUserToUserDTO(userRepository.saveAndFlush(user));
    }

    public UserDTO findById(Long id) {
        return convertUserToUserDTO(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }

    public UserDTO editUser(UserDTO userDTO) {
        User user = convertUserDTOToUser(userDTO);
        return convertUserToUserDTO(userRepository.saveAndFlush(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}