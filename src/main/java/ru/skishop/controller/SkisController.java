package ru.skishop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.request.SkiPageableFilter;
import ru.skishop.dto.PaginationWrapper;
import ru.skishop.dto.SkiDto;
import ru.skishop.service.SkiService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skis")
@Validated
@Api(tags = "Ski Controller")
public class SkisController {

    private final SkiService skiService;

    @GetMapping
    @Operation(summary = "Get list of skis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get list of skis from catalogue"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<PaginationWrapper<SkiDto>> getSkisWithCriteria(@Valid SkiPageableFilter skiPageableFilter) {
        PaginationWrapper<SkiDto> paginationWrapper = skiService.getSkisWithCriteria(skiPageableFilter);
        return ResponseEntity.ok(paginationWrapper);
    }

    @PostMapping
    @RolesAllowed("admin")
    @Operation(summary = "Create new ski")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new ski using Json body"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<SkiDto> create(@Valid @RequestBody SkiDto skiDto) {
        SkiDto ski = skiService.create(skiDto);
        return ResponseEntity.ok(ski);
    }

    @PutMapping
    @RolesAllowed("admin")
    @Operation(summary = "Edit ski")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit ski using Json body"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<SkiDto> edit(@Valid @RequestBody SkiDto skiDto) {
        SkiDto ski = skiService.edit(skiDto);
        return ResponseEntity.ok(ski);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete ski")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete ski by id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "You do not have access rights")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") @Min(message = "value must be greater than 0", value = 1) Long id) {
        skiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}