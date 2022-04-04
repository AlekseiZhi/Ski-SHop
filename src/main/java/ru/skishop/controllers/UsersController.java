package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.UserDto;
import ru.skishop.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class UsersController {

    private final UserService userService;

    @GetMapping("/all")
    @RolesAllowed("admin")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> findById(@PathVariable("id") @Min(message = "value must be greater than 0", value = 1) Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.createNewUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @RolesAllowed("admin")
    public ResponseEntity<UserDto> editUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.editUser(userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") @Min(message = "value must be greater than 0", value = 1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}