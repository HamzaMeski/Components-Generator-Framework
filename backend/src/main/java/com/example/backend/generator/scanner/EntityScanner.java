package com.example.backend.generator.scanner;

import com.example.backend.generator.model.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityScanner {
    private static final String BASE_PACKAGE = "com.example.backend";
    private static final String ENTITIES_PACKAGE = BASE_PACKAGE + ".entities";
    private final ValidationScanner validationScanner;

    public EntityScanner(ValidationScanner validationScanner) {
        this.validationScanner = validationScanner;
    }

    public List<String> findAllEntities() {
        try {
            return ClassPathScanner.findClassesWithAnnotation(ENTITIES_PACKAGE, Entity.class)
                    .stream()
                    .map(Class::getSimpleName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan entities", e);
        }
    }

    public EntityMetadata scanEntity(String entityName) {
        try {
            Class<?> entityClass = Class.forName(ENTITIES_PACKAGE + "." + entityName);
            if (!entityClass.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("Class is not an entity: " + entityName);
            }

            return EntityMetadata.builder()
                    .entityName(entityName)
                    .entityNameLower(entityName.toLowerCase())
                    .basePackage(BASE_PACKAGE)
                    .fields(scanFields(entityClass))
                    .relationships(scanRelationships(entityClass))
                    .idType(findIdType(entityClass))
                    .build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Entity not found: " + entityName, e);
        }
    }

    private List<FieldMetadata> scanFields(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::isBasicField)
                .map(this::createFieldMetadata)
                .collect(Collectors.toList());
    }

    private boolean isBasicField(Field field) {
        return !field.isAnnotationPresent(OneToMany.class) &&
                !field.isAnnotationPresent(ManyToOne.class) &&
                !field.isAnnotationPresent(OneToOne.class) &&
                !field.isAnnotationPresent(ManyToMany.class);
    }

    private FieldMetadata createFieldMetadata(Field field) {
        Column column = field.getAnnotation(Column.class);
        return FieldMetadata.builder()
                .name(field.getName())
                .type(field.getType().getSimpleName())
                .isId(field.isAnnotationPresent(Id.class))
                .validations(validationScanner.scanValidations(field))
                .columnName(column != null ? column.name() : field.getName())
                .nullable(column != null ? column.nullable() : true)
                .build();
    }

    private List<RelationshipMetadata> scanRelationships(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::isRelationshipField)
                .map(this::createRelationshipMetadata)
                .collect(Collectors.toList());
    }

    private boolean isRelationshipField(Field field) {
        return field.isAnnotationPresent(OneToMany.class) ||
                field.isAnnotationPresent(ManyToOne.class) ||
                field.isAnnotationPresent(OneToOne.class) ||
                field.isAnnotationPresent(ManyToMany.class);
    }

    private RelationshipMetadata createRelationshipMetadata(Field field) {
        RelationshipMetadata.RelationshipMetadataBuilder builder = RelationshipMetadata.builder()
                .fieldName(field.getName())
                .targetEntity(getTargetEntity(field));

        if (field.isAnnotationPresent(ManyToOne.class)) {
            ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
            JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);

            builder.type(RelationType.MANY_TO_ONE)
                    .fetchType(manyToOne.fetch())
                    .required(joinColumn != null && !joinColumn.nullable())
                    .joinColumnName(joinColumn != null ? joinColumn.name() : null);
        } else if (field.isAnnotationPresent(OneToMany.class)) {
            OneToMany oneToMany = field.getAnnotation(OneToMany.class);

            builder.type(RelationType.ONE_TO_MANY)
                    .mappedBy(oneToMany.mappedBy())
                    .fetchType(oneToMany.fetch())
                    .cascadeTypes(oneToMany.cascade())
                    .orphanRemoval(oneToMany.orphanRemoval());
        }

        return builder.build();
    }

    private String getTargetEntity(Field field) {
        if (field.getType().equals(List.class)) {
            return getGenericType(field);
        }
        return field.getType().getSimpleName();
    }

    private String getGenericType(Field field) {
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        return ((Class<?>) type.getActualTypeArguments()[0]).getSimpleName();
    }

    private String findIdType(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .map(f -> f.getType().getSimpleName())
                .orElse("Long");
    }
}
