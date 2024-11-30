package com.example.backend.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name shouldn't be blank")
    private String firstName;

    @NotBlank(message = "last name shouldn't be blank")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "make sure the email you set is valid")
    private String email;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate registrationDate;
}
