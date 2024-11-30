package com.example.backend.EntityComponentsProvider.service;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.mapper.EntityMapper;
import com.example.backend.EntityComponentsProvider.repository.EntityRepository;
import com.example.backend.config.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class EntityServiceImpl<T, ID> implements EntityService<T, ID> {
    
    protected final EntityRepository<T, ID> entityRepository;
    protected final EntityMapper<T, ID> entityMapper;

    @Override
    @Transactional
    public ResponseDTO<T, ID> create(CreateDTO<T> request) {
        T entity = entityMapper.toEntity(request);
        entity = entityRepository.save(entity);
        return entityMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void update(ID id, UpdateDTO<T> request) {
        T entity = checkEntityExistence(id);
        entityMapper.updateEntity(request, entity);
        entityRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseDTO<T, ID>> getAll(Pageable pageable) {
        return entityRepository.findAll(pageable)
                .map(entityMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDTO<T, ID> getById(ID id) {
        return entityMapper.toResponseDTO(checkEntityExistence(id));
    }

    @Override
    @Transactional
    public void delete(ID id) {
        entityRepository.delete(checkEntityExistence(id));
    }

    // Validation
    protected T checkEntityExistence(ID id) {
        return entityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
    }
}
