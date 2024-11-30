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

@RequiredArgsConstructor
public abstract class Controller<T, ID> {
    
    protected final EntityService<T, ID> entityService;

    @PostMapping
    public ResponseEntity<ResponseDTO<T, ID>> create(@Valid @RequestBody CreateDTO<T> request) {
        return new ResponseEntity<>(entityService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable ID id, @Valid @RequestBody UpdateDTO<T> request) {
        entityService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ResponseDTO<T, ID>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(entityService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<T, ID>> getById(@PathVariable ID id) {
        return ResponseEntity.ok(entityService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        entityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
