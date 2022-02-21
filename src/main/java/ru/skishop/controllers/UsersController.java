package ru.skishop.controllers;

import org.springframework.web.bind.annotation.*;
import ru.skishop.DTO.UserDTO;
import ru.skishop.service.UserService;

@RestController
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO findById(@RequestParam(value = "id") Long id) {
        System.out.println("работает контроллер на поиск по id = " + id);
        return userService.findById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        System.out.println("работает контроллер на создание Юзера");
        return userService.createNewUser(userDTO);
    }

    @PutMapping
    public UserDTO editUser(@RequestBody UserDTO userDTO) {
        System.out.println("работает контроллер на изменение Юзера");
        return userService.editUser(userDTO);
    }

    @DeleteMapping
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        System.out.println("Контроллер для удаления Юзера отработал успешно");
        return "User с id=" + id + " был удален";
    }
}