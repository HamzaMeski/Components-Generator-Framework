package com.example.backend.EntityComponentsProvider.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.mapper.EntityMapper;
import com.example.backend.EntityComponentsProvider.repository.EntityRepository;
import com.example.backend.config.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntityServiceImpl<T, ID, C extends CreateDTO<T>, U extends UpdateDTO<T>, R extends ResponseDTO<T, ID>> 
        implements EntityService<T, ID, C, U, R> {
    
    protected final EntityRepository<T, ID> entityRepository;
    protected final EntityMapper<T, ID, C, U, R> entityMapper;

    @Override
    @Transactional
    public R create(C request) {
        T entity = entityMapper.toEntity(request);
        entity = entityRepository.save(entity);
        return entityMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void update(ID id, U request) {
        T entity = checkEntityExistence(id);
        entityMapper.updateEntity(request, entity);
        entityRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<R> getAll(Pageable pageable) {
        return entityRepository.findAll(pageable)
                .map(entityMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public R getById(ID id) {
        return entityMapper.toResponseDTO(checkEntityExistence(id));
    }

    @Override
    @Transactional
    public void delete(ID id) {
        entityRepository.delete(checkEntityExistence(id));
    }

    protected T checkEntityExistence(ID id) {
        return entityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity with id " + id + " not found"));
    }
}
