package ru.skishop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.SkiDto;
import ru.skishop.dto.UserDto;
import ru.skishop.service.SkiService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ski")
public class SkiController {

    private final SkiService skiService;

    @GetMapping("/all")
    public ResponseEntity<List<SkiDto>> findAllUsers() {
        List<SkiDto> skis = skiService.findAllSkis();
        return ResponseEntity.ok(skis);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<SkiDto> create(@RequestBody SkiDto skiDTO) {
        SkiDto skiDto = skiService.create(skiDTO);
        return ResponseEntity.ok(skiDto);
    }

    @PutMapping
    @RolesAllowed("admin")
    public ResponseEntity<SkiDto> edit(@RequestBody SkiDto skiDTO) {
        SkiDto skiDto = skiService.edit(skiDTO);
        return ResponseEntity.ok(skiDto);
    }

    @DeleteMapping
    @RolesAllowed("admin")
    public ResponseEntity<Void> delete(@RequestParam(value = "id") Long id) {
        skiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}