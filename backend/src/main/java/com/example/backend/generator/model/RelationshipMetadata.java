package com.example.backend.generator.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationshipMetadata {
    private String fieldName;
    private String targetEntity;
    private RelationType type;
    private boolean required;
    private String mappedBy;
    private FetchType fetchType;
    private CascadeType[] cascadeTypes;
    private String joinColumnName;
    private boolean orphanRemoval;
}