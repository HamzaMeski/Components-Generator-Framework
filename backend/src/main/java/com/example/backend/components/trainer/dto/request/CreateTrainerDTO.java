package com.example.backend.components.trainer.dto.request;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.RelationshipField;
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
public class CreateTrainerDTO extends CreateDTO<Trainer> {
    @NotBlank(message = "first name shouldn't be blank")
    private String firstName;

    @NotBlank(message = "last name shouldn't be blank")
    private String lastName;

    @NotBlank(message = "email is required")
    private String email;

    @NotNull(message = "{jakarta.validation.constraints.NotNull.message}")
    private LocalDate birthDate;

    @NotNull(message = "{jakarta.validation.constraints.NotNull.message}")
    private LocalDate registrationDate;

}
