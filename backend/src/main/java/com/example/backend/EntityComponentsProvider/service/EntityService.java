package com.example.backend.EntityComponentsProvider.service;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface EntityService {
    ResponseDTO create(CreateDTO request);
    void update(Long id, UpdateDTO request);
    Page<ResponseDTO> getAll(Pageable pageable);
    ResponseDTO getById(Long id);
    void delete(Long id);
}
