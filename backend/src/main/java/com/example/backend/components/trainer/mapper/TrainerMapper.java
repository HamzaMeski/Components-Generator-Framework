package com.example.backend.components.trainer.mapper;

import com.example.backend.EntityComponentsProvider.mapper.EntityMapper;
import com.example.backend.entities.Trainer;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface TrainerMapper extends EntityMapper<Trainer, Long,
    CreateTrainerDTO, UpdateTrainerDTO, TrainerResponseDTO> {

    
    Trainer toEntity(CreateTrainerDTO createDTO);

    
    void updateEntity(UpdateTrainerDTO updateDTO, @MappingTarget Trainer entity);

    TrainerResponseDTO toResponseDTO(Trainer entity);
}
