package com.example.backend.generator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationMetadata {
    private String annotationType;
    private String message;
    private String value;
    private String[] groups;
    private String[] payload;
}