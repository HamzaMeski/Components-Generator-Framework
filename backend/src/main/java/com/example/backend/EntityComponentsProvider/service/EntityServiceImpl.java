package com.example.backend.EntityComponentsProvider.service;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.mapper.EntityMapper;
import com.example.backend.EntityComponentsProvider.repository.EntityRepository;
import com.example.backend.config.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final EntityRepository entityRepository;
    private final EntityMapper entityMapper;

    @Override
    public ResponseDTO create(CreateDTO request) {
        Entity entity = entityMapper.toEntity(request);
        return entityMapper.toResponseDTO(entityRepository.save(entity));
    }

    @Override
    public void update(Long id, UpdateDTO request) {
        Entity entity = checkEntityExistence(id);
        entityMapper.updateEntity(request, entity);
        entityRepository.save(entity);
    }

    @Override
    public Page<ResponseDTO> getAll(Pageable pageable) {
        return entityRepository.findAll(pageable)
                .map(entityMapper::toResponseDTO);
    }

    @Override
    public ResponseDTO getById(Long id) {
        return entityMapper.toResponseDTO(checkEntityExistence(id));
    }

    @Override
    public void delete(Long id) {
        entityRepository.delete(checkEntityExistence(id));
    }

    // Validation
    public Entity checkEntityExistence(Long id) {
        return entityRepository.findById(id).
                orElseThrow(() -> new ValidationException("Entity doesn't exist with id " + id));
    }
}
