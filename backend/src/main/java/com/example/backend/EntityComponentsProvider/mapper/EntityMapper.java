package com.example.backend.EntityComponentsProvider.mapper;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EntityMapper <Entity> {
    Entity toEntity(CreateDTO request);

    ResponseDTO toResponseDTO(Entity entity);

    void updateEntity(UpdateDTO request, @MappingTarget Entity entity);
}
