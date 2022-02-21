package ru.skishop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skishop.DTO.UserDto;
import ru.skishop.service.UserService;

@AllArgsConstructor
@RestController
public class UsersController {

    private final UserService userService;

    @GetMapping
    public UserDto findById(@RequestParam(value = "id") Long id) {
        System.out.println("работает контроллер на поиск по id = " + id);
        return userService.findById(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDTO) {
        System.out.println("работает контроллер на создание Юзера");
        return userService.createNewUser(userDTO);
    }

    @PutMapping
    public UserDto editUser(@RequestBody UserDto userDTO) {
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