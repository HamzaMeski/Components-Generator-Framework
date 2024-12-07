package com.example.backend.generator.template;

import com.example.backend.generator.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TemplateEngine {

    public String generateController(EntityMetadata metadata) {
        return """
            package ${basePackage}.components.${entityNameLower}.controller;
            
            import ${basePackage}.EntityComponentsProvider.controller.Controller;
            import ${basePackage}.components.${entityNameLower}.dto.request.Create${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.request.Update${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.response.${entityName}ResponseDTO;
            import ${basePackage}.components.${entityNameLower}.service.${entityName}Service;
            import ${basePackage}.entities.${entityName};
            import org.springframework.web.bind.annotation.RequestMapping;
            import org.springframework.web.bind.annotation.RestController;
            
            @RestController
            @RequestMapping("/api/${entityNameLower}s")
            public class ${entityName}Controller extends Controller<${entityName}, ${idType}, Create${entityName}DTO, Update${entityName}DTO, ${entityName}ResponseDTO> {
                private final ${entityName}Service ${entityNameLower}Service;
                
                public ${entityName}Controller(${entityName}Service ${entityNameLower}Service) {
                    super(${entityNameLower}Service);
                    this.${entityNameLower}Service = ${entityNameLower}Service;
                }
            }
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${idType}", metadata.getIdType());
    }

    public String generateService(EntityMetadata metadata) {
        return """
            package ${basePackage}.components.${entityNameLower}.service;
            
            import ${basePackage}.EntityComponentsProvider.service.EntityServiceImpl;
            import ${basePackage}.components.${entityNameLower}.dto.request.Create${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.request.Update${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.response.${entityName}ResponseDTO;
            import ${basePackage}.components.${entityNameLower}.mapper.${entityName}Mapper;
            import ${basePackage}.components.${entityNameLower}.repository.${entityName}Repository;
            import ${basePackage}.entities.${entityName};
            import lombok.extern.slf4j.Slf4j;
            import org.springframework.context.ApplicationContext;
            import org.springframework.stereotype.Service;
            import org.springframework.transaction.annotation.Transactional;
            
            @Service
            @Slf4j
            @Transactional
            public class ${entityName}Service extends EntityServiceImpl<${entityName}, ${idType}, Create${entityName}DTO, Update${entityName}DTO, ${entityName}ResponseDTO> {
                private final ${entityName}Repository ${entityNameLower}Repository;
                private final ${entityName}Mapper ${entityNameLower}Mapper;
                
                public ${entityName}Service(
                        ${entityName}Repository ${entityNameLower}Repository,
                        ${entityName}Mapper ${entityNameLower}Mapper,
                        ApplicationContext applicationContext) {
                    super(${entityNameLower}Repository, ${entityNameLower}Mapper, applicationContext);
                    this.${entityNameLower}Repository = ${entityNameLower}Repository;
                    this.${entityNameLower}Mapper = ${entityNameLower}Mapper;
                }
            }
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${idType}", metadata.getIdType());
    }

    public String generateRepository(EntityMetadata metadata) {
        return """
            package ${basePackage}.components.${entityNameLower}.repository;
            
            import ${basePackage}.EntityComponentsProvider.repository.EntityRepository;
            import ${basePackage}.entities.${entityName};
            
            public interface ${entityName}Repository extends EntityRepository<${entityName}, ${idType}> {
            }
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${idType}", metadata.getIdType());
    }

    public String generateMapper(EntityMetadata metadata) {
        String usedMappers = metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .map(rel -> rel.getTargetEntity() + "Mapper.class")
                .collect(Collectors.joining(", "));

        String mapperImports = metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .map(rel -> String.format("import %s.components.%s.mapper.%sMapper;",
                        metadata.getBasePackage(),
                        rel.getTargetEntity().toLowerCase(),
                        rel.getTargetEntity()))
                .collect(Collectors.joining("\n"));

        String mappings = metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .map(rel -> "@Mapping(target = \"" + rel.getFieldName() + "\", ignore = true)")
                .collect(Collectors.joining("\n    "));

        return """
            package ${basePackage}.components.${entityNameLower}.mapper;
            
            import ${basePackage}.EntityComponentsProvider.mapper.EntityMapper;
            import ${basePackage}.entities.${entityName};
            import ${basePackage}.components.${entityNameLower}.dto.request.Create${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.request.Update${entityName}DTO;
            import ${basePackage}.components.${entityNameLower}.dto.response.${entityName}ResponseDTO;
            import org.mapstruct.Mapper;
            import org.mapstruct.Mapping;
            import org.mapstruct.MappingTarget;
            ${mapperImports}
            
            @Mapper(componentModel = "spring"${usedMappers})
            public interface ${entityName}Mapper extends EntityMapper<${entityName}, ${idType}, 
                Create${entityName}DTO, Update${entityName}DTO, ${entityName}ResponseDTO> {
                
                ${mappings}
                ${entityName} toEntity(Create${entityName}DTO createDTO);
                
                ${mappings}
                void updateEntity(Update${entityName}DTO updateDTO, @MappingTarget ${entityName} entity);
                
                ${entityName}ResponseDTO toResponseDTO(${entityName} entity);
            }
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${idType}", metadata.getIdType())
                .replace("${usedMappers}", usedMappers.isEmpty() ? "" : ", uses = { " + usedMappers + " }")
                .replace("${mappings}", mappings)
                .replace("${mapperImports}", mapperImports);
    }

    private String generateImports(EntityMetadata metadata) {
        StringBuilder imports = new StringBuilder();

        // Add standard imports
        imports.append("import java.time.*;\n");
        imports.append("import java.util.*;\n");
        imports.append("import java.math.*;\n");

        // Add validation imports
        imports.append("import jakarta.validation.constraints.*;\n");

        return imports.toString();
    }

    private String generateValidationAnnotations(List<ValidationMetadata> validations) {
        StringBuilder annotations = new StringBuilder();
        validations.forEach(validation -> {
            annotations.append("    @").append(validation.getAnnotationType());
            if (validation.getValue() != null) {
                annotations.append("(value = ").append(validation.getValue())
                          .append(", message = \"").append(validation.getMessage()).append("\")");
            } else {
                annotations.append("(message = \"").append(validation.getMessage()).append("\")");
            }
            annotations.append("\n");
        });
        return annotations.toString();
    }

    public String generateCreateDTO(EntityMetadata metadata) {
        StringBuilder fields = new StringBuilder();

        // Add relationship fields
        metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .forEach(rel -> {
                    fields.append("""
                    
                    @RelationshipField(
                            entity = ${targetEntity}.class,
                            repository = "${basePackage}.components.${targetEntityLower}.repository.${targetEntity}Repository"
                    )
                    @NotNull(message = "${fieldName} is required")
                    private Long ${fieldName}Id;
                    
                    """.replace("${targetEntity}", rel.getTargetEntity())
                            .replace("${targetEntityLower}", rel.getTargetEntity().toLowerCase())
                            .replace("${fieldName}", rel.getFieldName())
                            .replace("${basePackage}", metadata.getBasePackage()));
                });

        // Add basic fields
        metadata.getFields().stream()
                .filter(field -> !field.isId())
                .forEach(field -> {
                    fields.append(generateValidationAnnotations(field.getValidations()));
                    fields.append("    private ").append(field.getType())
                            .append(" ").append(field.getName()).append(";\n\n");
                });

        return """
            package ${basePackage}.components.${entityNameLower}.dto.request;
            
            import ${basePackage}.EntityComponentsProvider.dto.request.CreateDTO;
            import ${basePackage}.EntityComponentsProvider.dto.request.RelationshipField;
            import ${basePackage}.entities.*;
            ${imports}
            import lombok.*;
            import lombok.experimental.SuperBuilder;
            
            @Data
            @SuperBuilder
            @NoArgsConstructor
            @EqualsAndHashCode(callSuper = true)
            public class Create${entityName}DTO extends CreateDTO<${entityName}> {
            ${fields}}
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${imports}", generateImports(metadata))
                .replace("${fields}", fields.toString());
    }

    public String generateUpdateDTO(EntityMetadata metadata) {
        StringBuilder fields = new StringBuilder();

        // Add relationship fields
        metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .forEach(rel -> {
                    fields.append("""
                    
                    @RelationshipField(
                            entity = ${targetEntity}.class,
                            repository = "${basePackage}.components.${targetEntityLower}.repository.${targetEntity}Repository"
                    )
                    private Long ${fieldName}Id;
                    
                    """.replace("${targetEntity}", rel.getTargetEntity())
                            .replace("${targetEntityLower}", rel.getTargetEntity().toLowerCase())
                            .replace("${fieldName}", rel.getFieldName())
                            .replace("${basePackage}", metadata.getBasePackage()));
                });

        // Add basic fields
        metadata.getFields().stream()
                .filter(field -> !field.isId())
                .forEach(field -> {
                    fields.append(generateValidationAnnotations(field.getValidations()));
                    fields.append("    private ").append(field.getType())
                            .append(" ").append(field.getName()).append(";\n\n");
                });

        return """
            package ${basePackage}.components.${entityNameLower}.dto.request;
            
            import ${basePackage}.EntityComponentsProvider.dto.request.UpdateDTO;
            import ${basePackage}.EntityComponentsProvider.dto.request.RelationshipField;
            import ${basePackage}.entities.*;
            ${imports}
            import lombok.*;
            import lombok.experimental.SuperBuilder;
            
            @Data
            @SuperBuilder
            @NoArgsConstructor
            @EqualsAndHashCode(callSuper = true)
            public class Update${entityName}DTO extends UpdateDTO<${entityName}> {
            ${fields}}
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${imports}", generateImports(metadata))
                .replace("${fields}", fields.toString());
    }

    public String generateResponseDTO(EntityMetadata metadata) {
        StringBuilder fields = new StringBuilder();

        // Add relationship fields
        metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .forEach(rel -> {
                    fields.append("""
                    
                    private ${targetEntity}ResponseDTO ${fieldName};
                    
                    """.replace("${targetEntity}", rel.getTargetEntity())
                            .replace("${fieldName}", rel.getFieldName()));
                });

        // Add basic fields
        metadata.getFields().forEach(field -> {
            fields.append("    private ").append(field.getType())
                    .append(" ").append(field.getName()).append(";\n\n");
        });

        // Build imports for related DTOs
        String dtoImports = metadata.getRelationships().stream()
                .filter(rel -> rel.getType() == RelationType.MANY_TO_ONE)
                .map(rel -> "import " + metadata.getBasePackage() + ".components." +
                        rel.getTargetEntity().toLowerCase() + ".dto.response." +
                        rel.getTargetEntity() + "ResponseDTO;")
                .collect(Collectors.joining("\n"));

        return """
            package ${basePackage}.components.${entityNameLower}.dto.response;
            
            import ${basePackage}.EntityComponentsProvider.dto.response.ResponseDTO;
            import ${basePackage}.entities.*;
            ${imports}
            ${dtoImports}
            import lombok.*;
            import lombok.experimental.SuperBuilder;
            
            @Data
            @SuperBuilder
            @NoArgsConstructor
            @EqualsAndHashCode(callSuper = true)
            public class ${entityName}ResponseDTO extends ResponseDTO<${entityName}, Long> {
            ${fields}}
            """.replace("${entityName}", metadata.getEntityName())
                .replace("${entityNameLower}", metadata.getEntityNameLower())
                .replace("${basePackage}", metadata.getBasePackage())
                .replace("${imports}", generateImports(metadata))
                .replace("${dtoImports}", dtoImports)
                .replace("${fields}", fields.toString());
    }
}
