package ru.skishop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
//@Tag(name = "UserController", description = "User entity operations")
@Api(tags = "User Controller")
public class UsersController {

    private final UserService userService;

    @GetMapping("/all")
    @RolesAllowed("admin")
    @Operation(summary = "Get list of users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get list of users from catalogue")
    })
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Get user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get user by id")
    })
    public ResponseEntity<UserDto> findById(@PathVariable("id") @Min(message = "value must be greater than 0", value = 1) Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @RolesAllowed("admin")
    @Operation(summary = "Create new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new user using Json body")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.createNewUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @RolesAllowed("admin")
    @Operation(summary = "Edit user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit user using Json body")
    })
    public ResponseEntity<UserDto> editUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.editUser(userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete user by id")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") @Min(message = "value must be greater than 0", value = 1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}