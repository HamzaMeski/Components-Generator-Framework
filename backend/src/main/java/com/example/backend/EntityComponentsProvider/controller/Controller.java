package com.example.backend.EntityComponentsProvider.controller;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.service.EntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class Controller {
    private final EntityService entityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO create(
            @Valid @RequestBody CreateDTO request
    ) {
        return entityService.create(request);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDTO request
    ) {
        entityService.update(id, request);
    }

    @GetMapping
    public Page<ResponseDTO> getAll(Pageable pageable) {
        return entityService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseDTO getById(
            @PathVariable Long id
    ) {
        return entityService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        entityService.delete(id);
    }
}
