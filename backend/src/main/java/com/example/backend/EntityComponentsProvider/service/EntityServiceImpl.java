package com.example.backend.EntityComponentsProvider.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.RelationshipField;
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
    protected final ApplicationContext applicationContext;

    @Override
    @Transactional
    public R create(C request) {
        T entity = entityMapper.toEntity(request);
        
        // Handle relationships
        handleRelationships(request, entity);
        
        entity = entityRepository.save(entity);
        return entityMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void update(ID id, U request) {
        T entity = checkEntityExistence(id);
        entityMapper.updateEntity(request, entity);
        
        // Handle relationships for update
        handleRelationships(request, entity);
        
        entityRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public R getById(ID id) {
        T entity = checkEntityExistence(id);
        return entityMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<R> getAll(Pageable pageable) {
        return entityRepository.findAll(pageable).map(entityMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        if (!entityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entity with id " + id + " not found");
        }
        entityRepository.deleteById(id);
    }

    protected T checkEntityExistence(ID id) {
        return entityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity with id " + id + " not found"));
    }

    @SuppressWarnings("unchecked")
    protected void handleRelationships(Object request, T entity) {
        for (Field field : request.getClass().getDeclaredFields()) {
            RelationshipField annotation = field.getAnnotation(RelationshipField.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Object relationId = field.get(request);
                    if (relationId != null) {
                        try {
                            // Get repository class
                            Class<?> repositoryType = Class.forName(annotation.repository());
                            
                            // Get repository instance
                            JpaRepository<Object, Object> repository = 
                                (JpaRepository<Object, Object>) applicationContext.getBean(repositoryType);
                            
                            // Convert ID to Long (assuming all IDs are Long)
                            Long typedId = relationId instanceof Number ? 
                                ((Number) relationId).longValue() : 
                                Long.parseLong(relationId.toString());
                            
                            // Find the related entity using repository's findById
                            Method findByIdMethod = repositoryType.getMethod("findById", Object.class);
                            Object relatedEntity = ((java.util.Optional<?>) findByIdMethod.invoke(repository, typedId))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                    "Related entity " + annotation.entity().getSimpleName() + 
                                    " with id " + relationId + " not found"));
                            
                            // Set the related entity on the main entity
                            String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + 
                                              field.getName().substring(1).replace("Id", "");
                            entity.getClass()
                                 .getMethod(setterName, annotation.entity())
                                 .invoke(entity, relatedEntity);
                        } catch (BeansException e) {
                            throw new RuntimeException("Could not find repository bean: " + 
                                                    annotation.repository(), e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("Could not find repository class: " + 
                                                    annotation.repository(), e);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error handling relationship for field " + 
                                            field.getName(), e);
                }
            }
        }
    }
}
