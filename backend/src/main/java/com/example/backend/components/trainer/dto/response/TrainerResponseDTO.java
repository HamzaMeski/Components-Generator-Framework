package com.example.backend.components.trainer.dto.response;

import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.entities.*;
import java.time.*;
import java.util.*;
import java.math.*;
import jakarta.validation.constraints.*;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrainerResponseDTO extends ResponseDTO<Trainer, Long> {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthDate;

    private LocalDate registrationDate;

}
