package com.example.backend.components.trainer.dto.response;

import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.entities.Trainer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrainerResponseDTO extends ResponseDTO<Trainer, Long> {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private LocalDate registrationDate;
}