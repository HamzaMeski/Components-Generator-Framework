package com.example.backend.generator.scanner;

import com.example.backend.generator.model.ValidationMetadata;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationScanner {

    public List<ValidationMetadata> scanValidations(Field field) {
        List<ValidationMetadata> validations = new ArrayList<>();

        // Handle @NotNull
        NotNull notNull = field.getAnnotation(NotNull.class);
        if (notNull != null) {
            validations.add(ValidationMetadata.builder()
                    .annotationType("NotNull")
                    .message(notNull.message())
                    .build());
        }

        // Handle @NotBlank
        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (notBlank != null) {
            validations.add(ValidationMetadata.builder()
                    .annotationType("NotBlank")
                    .message(notBlank.message())
                    .build());
        }

        // Handle @Size
        Size size = field.getAnnotation(Size.class);
        if (size != null) {
            validations.add(ValidationMetadata.builder()
                    .annotationType("Size")
                    .message(size.message())
                    .build());
        }

        // Handle @Min
        Min min = field.getAnnotation(Min.class);
        if (min != null) {
            validations.add(ValidationMetadata.builder()
                    .annotationType("Min")
                    .message(min.message())
                    .value(String.valueOf(min.value()))
                    .build());
        }

        // Handle @Max
        Max max = field.getAnnotation(Max.class);
        if (max != null) {
            validations.add(ValidationMetadata.builder()
                    .annotationType("Max")
                    .message(max.message())
                    .value(String.valueOf(max.value()))
                    .build());
        }

        return validations;
    }
}
