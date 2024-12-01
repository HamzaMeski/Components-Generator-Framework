package com.example.backend.EntityComponentsProvider.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ResponseDTO<T, ID> {
    protected ID id;
}