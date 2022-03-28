package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.UserDto;
import ru.skishop.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final UserService userService;

    @GetMapping("/all")
    @RolesAllowed("admin")
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping()
    @RolesAllowed("admin")
    public UserDto findById(@RequestParam(value = "id") Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @RolesAllowed("admin")
    public UserDto createUser(@RequestBody UserDto userDTO) {
        return userService.createNewUser(userDTO);
    }

    @PutMapping
    @RolesAllowed("admin")
    public UserDto editUser(@RequestBody UserDto userDTO) {
        return userService.editUser(userDTO);
    }

    @DeleteMapping
    @RolesAllowed("admin")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "User с id=" + id + " был удален";
    }
}