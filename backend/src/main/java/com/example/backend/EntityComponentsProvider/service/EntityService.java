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
    /**
     * Creates a new entity
     * @param request The create DTO
     * @return The created entity as response DTO
     */
    R create(@Valid C request);

    /**
     * Updates an existing entity
     * @param id The entity ID
     * @param request The update DTO
     */
    void update(ID id, @Valid U request);

    /**
     * Finds an entity by ID
     * @param id The entity ID
     * @return The entity as response DTO
     */
    R getById(ID id);

    /**
     * Finds all entities with pagination
     * @param pageable The pagination information
     * @return A page of entities as response DTOs
     */
    Page<R> getAll(Pageable pageable);

    /**
     * Deletes an entity by ID
     * @param id The entity ID
     */
    void delete(ID id);
}
