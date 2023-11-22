package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.service.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Accommodations CRUD API", description = "Endpoints to access accommodations")
@RestController
@Validated
@RequestMapping(value = "/accommodations")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all accommodations")
    public List<AccommodationDto> getAll(Pageable pageable) {
        return accommodationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get accommodation by id")
    public AccommodationDto getCategoryById(@PathVariable @Positive Long id) {
        return accommodationService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new accommodation")
    public AccommodationDto createCategory(@RequestBody @Valid AccommodationRequestDto requestDto) {
        return accommodationService.save(requestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update accommodation by id")
    public AccommodationDto updateCategory(@PathVariable @Positive Long id,
                                      @RequestBody @Valid AccommodationRequestDto createDto) {
        return accommodationService.update(id, createDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete accommodation by id")
    public void deleteCategory(@PathVariable @Positive Long id) {
        accommodationService.deleteById(id);
    }
}
