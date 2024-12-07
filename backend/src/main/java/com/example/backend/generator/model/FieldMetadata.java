package com.example.backend.generator.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class FieldMetadata {
    private String name;
    private String type;
    private boolean isId;
    private List<ValidationMetadata> validations;
    private String columnName;
    private boolean nullable;
}