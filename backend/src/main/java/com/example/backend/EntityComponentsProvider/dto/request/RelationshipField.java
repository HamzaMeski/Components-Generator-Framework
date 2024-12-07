package com.example.backend.EntityComponentsProvider.dto.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RelationshipField {
    Class<?> entity();  // The entity class type
    String repository(); // The repository bean name to use for lookup
}