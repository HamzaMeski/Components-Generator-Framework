package com.example.backend.EntityComponentsProvider.mapper;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EntityMapper<T, ID> {
    T toEntity(CreateDTO<T> request);

    ResponseDTO<T, ID> toResponseDTO(T entity);

    void updateEntity(UpdateDTO<T> request, @MappingTarget T entity);
}
