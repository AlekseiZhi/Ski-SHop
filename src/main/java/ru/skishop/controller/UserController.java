package ru.skishop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Api(tags = "User Controller")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @RolesAllowed("admin")
    @Operation(summary = "Get list of users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get list of users from catalogue"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Get user by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get user by id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<UserDto> findById(@PathVariable("id") @Min(1) Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @RolesAllowed("admin")
    @Operation(summary = "Create new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new user using Json body"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.createNewUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @RolesAllowed("admin")
    @Operation(summary = "Edit user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit user using Json body"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<UserDto> editUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.editUser(userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete user by id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") @Min(1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}