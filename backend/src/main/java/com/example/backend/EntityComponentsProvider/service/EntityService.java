package com.example.backend.EntityComponentsProvider.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;

public interface EntityService<T, ID> {
    ResponseDTO<T, ID> create(CreateDTO<T> request);
    void update(ID id, UpdateDTO<T> request);
    Page<ResponseDTO<T, ID>> getAll(Pageable pageable);
    ResponseDTO<T, ID> getById(ID id);
    void delete(ID id);
}
