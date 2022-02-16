package ru.skishop.controllers;

import org.springframework.web.bind.annotation.*;
import ru.skishop.DTO.UserDTO;
import ru.skishop.service.UserService;

@RestController
public class UsersController {

    private UserService userService;

    @GetMapping
    public UserDTO findById(@RequestParam("id") Long id) {
        System.out.println("работает контроллер на поиск по id");
        return userService.findById(id);
    }

    @PostMapping
    public UserDTO createUser(UserDTO userDTO) {
        System.out.println("работает контроллер на создание Юзера");
        return userService.createNewUser(userDTO);
    }

//    @PostMapping(value = "/create")
//    public UserDTO createUser_1(
//            @RequestParam(name = "name") String name,
//            @RequestParam(name = "email") String email,
//            @RequestParam(name = "password") String password
//            ) {
//        return null;
//    }
//
//    @PostMapping(value = "/body")
//    public UserDTO createUser_2(
//            @RequestBody UserDTO userDTO
//    ) {
//        return userService.create();
//    }

    @PutMapping
    public UserDTO editUser(UserDTO userDTO) {
        System.out.println("работает контроллер на изменение Юзера");
        return userService.editUser(userDTO);
    }

    @DeleteMapping
    public void deleteUser(Long id) {
        userService.deleteUser(id);
        System.out.println("Контроллер для удаления Юзера отработал успешно");
    }
}