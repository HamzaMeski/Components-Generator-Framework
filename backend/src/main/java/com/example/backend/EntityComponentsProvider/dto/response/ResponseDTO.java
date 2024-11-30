package com.example.backend.EntityComponentsProvider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T, ID> {
    protected ID id;
    // Base class for response DTOs
    // Specific fields will be added in generated implementations
}
