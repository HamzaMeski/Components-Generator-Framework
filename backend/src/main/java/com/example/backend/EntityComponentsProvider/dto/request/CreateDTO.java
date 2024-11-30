package com.example.backend.EntityComponentsProvider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDTO<T> {
    // Base class for create DTOs
    // Specific fields will be added in generated implementations
}
