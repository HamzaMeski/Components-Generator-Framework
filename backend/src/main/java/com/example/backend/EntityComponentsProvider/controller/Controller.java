package com.example.backend.EntityComponentsProvider.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.service.EntityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Base controller providing CRUD operations for entities.
 * @param <T> Entity type
 * @param <ID> Entity ID type
 * @param <C> Create DTO type
 * @param <U> Update DTO type
 * @param <R> Response DTO type
 */
@RequiredArgsConstructor
public abstract class Controller<T, ID, C extends CreateDTO<T>, U extends UpdateDTO<T>, R extends ResponseDTO<T, ID>> {
    
    protected final EntityService<T, ID, C, U, R> entityService;

    @PostMapping
    public ResponseEntity<R> create(@Valid @RequestBody C request) {
        return new ResponseEntity<>(entityService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable ID id, @Valid @RequestBody U request) {
        entityService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<R>> getAll(Pageable pageable) {
        return ResponseEntity.ok(entityService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable ID id) {
        return ResponseEntity.ok(entityService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        entityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
