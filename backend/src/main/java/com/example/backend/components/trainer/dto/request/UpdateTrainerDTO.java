package com.example.backend.components.trainer.dto.request;

import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.entities.Trainer;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateTrainerDTO extends UpdateDTO<Trainer> {
    @NotBlank(message = "first name shouldn't be blank")
    private String firstName;

    @NotBlank(message = "last name shouldn't be blank")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "make sure the email you set is valid")
    private String email;

    @NotNull(message = "birth date is required")
    private LocalDate birthDate;
}