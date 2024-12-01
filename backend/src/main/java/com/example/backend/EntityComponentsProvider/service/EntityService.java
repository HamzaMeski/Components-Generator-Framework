package com.example.backend.EntityComponentsProvider.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;

import jakarta.validation.Valid;

/**
 * Generic service interface for CRUD operations
 * @param <T> Entity type
 * @param <ID> Entity ID type
 * @param <C> Create DTO type
 * @param <U> Update DTO type
 * @param <R> Response DTO type
 */
@Validated
public interface EntityService<T, ID, C extends CreateDTO<T>, U extends UpdateDTO<T>, R extends ResponseDTO<T, ID>> {
    R create(@Valid C request);
    void update(ID id, @Valid U request);
    Page<R> getAll(Pageable pageable);
    R getById(ID id);
    void delete(ID id);
}
