package com.example.backend.components.trainer.mapper;

import com.example.backend.EntityComponentsProvider.mapper.EntityMapper;
import com.example.backend.entities.Trainer;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerDetailResponseDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring")
public interface TrainerMapper extends EntityMapper<Trainer, Long> {
    Trainer toEntity(CreateTrainerDTO request);

    TrainerResponseDTO toResponseDTO(Trainer entity);

    void updateEntity(UpdateTrainerDTO request, @MappingTarget Trainer entity);

    @Mapping(target = "age", source = "birthDate", qualifiedByName = "calculateAge")
    @Mapping(target = "yearsOfExperience", source = "registrationDate", qualifiedByName = "calculateExperience")
    TrainerDetailResponseDTO toDetailResponseDTO(Trainer entity);

    @Named("calculateAge")
    default Integer calculateAge(LocalDate birthDate) {
        return birthDate != null ? Period.between(birthDate, LocalDate.now()).getYears() : null;
    }

    @Named("calculateExperience")
    default Integer calculateExperience(LocalDate registrationDate) {
        return registrationDate != null ? Period.between(registrationDate, LocalDate.now()).getYears() : null;
    }
}