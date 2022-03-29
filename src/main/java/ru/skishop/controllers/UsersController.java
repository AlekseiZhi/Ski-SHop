package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> findById(@RequestParam(value = "id") Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto userDto = userService.createNewUser(userDTO);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDTO) {
        UserDto userDto = userService.editUser(userDTO);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping
    @RolesAllowed("admin")
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}