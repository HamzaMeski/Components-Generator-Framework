package com.example.backend.generator.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class EntityMetadata {
    private String entityName;
    private String entityNameLower;
    private String idType;
    private List<FieldMetadata> fields;
    private List<RelationshipMetadata> relationships;
    private String basePackage;

    public String getFullPackage(String component) {
        return String.format("%s.components.%s.%s", basePackage, entityNameLower, component);
    }

    public String getComponentPath(String component) {
        return String.format("src/main/java/%s/components/%s/%s",
                basePackage.replace(".", "/"), entityNameLower, component);
    }
}