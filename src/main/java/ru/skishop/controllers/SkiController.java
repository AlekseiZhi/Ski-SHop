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

    @GetMapping
    public ResponseEntity<List<SkiDto>> findAllSkis() {
        List<SkiDto> skis = skiService.findAllSkis();
        return ResponseEntity.ok(skis);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<SkiDto> create(@RequestBody SkiDto skiDto) {
        SkiDto ski = skiService.create(skiDto);
        return ResponseEntity.ok(ski);
    }

    @PutMapping
    @RolesAllowed("admin")
    public ResponseEntity<SkiDto> edit(@RequestBody SkiDto skiDto) {
        SkiDto ski = skiService.edit(skiDto);
        return ResponseEntity.ok(ski);
    }

    @DeleteMapping
    @RolesAllowed("admin")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        skiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}