package com.example.backend.generator;

import com.example.backend.generator.model.EntityMetadata;
import com.example.backend.generator.scanner.EntityScanner;
import com.example.backend.generator.template.TemplateEngine;
import com.example.backend.generator.util.FileWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ComponentGenerator {
    private final EntityScanner entityScanner;
    private final TemplateEngine templateEngine;
    private final FileWriter fileWriter;

    public void generateComponentsForEntity(String entityName) {
        EntityMetadata metadata = entityScanner.scanEntity(entityName);
        generateComponents(metadata);
    }

    public void generateComponentsForAllEntities() {
        List<String> entities = entityScanner.findAllEntities();
        entities.forEach(this::generateComponentsForEntity);
    }

    private void generateComponents(EntityMetadata metadata) {
        // Generate Controller
        String controllerPath = metadata.getComponentPath("controller") + "/" +
                metadata.getEntityName() + "Controller.java";
        fileWriter.writeFile(controllerPath, templateEngine.generateController(metadata));

        // Generate Service
        String servicePath = metadata.getComponentPath("service") + "/" +
                metadata.getEntityName() + "Service.java";
        fileWriter.writeFile(servicePath, templateEngine.generateService(metadata));

        // Generate Repository
        String repositoryPath = metadata.getComponentPath("repository") + "/" +
                metadata.getEntityName() + "Repository.java";
        fileWriter.writeFile(repositoryPath, templateEngine.generateRepository(metadata));

        // Generate Mapper
        String mapperPath = metadata.getComponentPath("mapper") + "/" +
                metadata.getEntityName() + "Mapper.java";
        fileWriter.writeFile(mapperPath, templateEngine.generateMapper(metadata));

        // Generate DTOs
        String dtoPath = metadata.getComponentPath("dto");

        // Create DTO
        String createDtoPath = dtoPath + "/request/Create" + metadata.getEntityName() + "DTO.java";
        fileWriter.writeFile(createDtoPath, templateEngine.generateCreateDTO(metadata));

        // Update DTO
        String updateDtoPath = dtoPath + "/request/Update" + metadata.getEntityName() + "DTO.java";
        fileWriter.writeFile(updateDtoPath, templateEngine.generateUpdateDTO(metadata));

        // Response DTO
        String responseDtoPath = dtoPath + "/response/" + metadata.getEntityName() + "ResponseDTO.java";
        fileWriter.writeFile(responseDtoPath, templateEngine.generateResponseDTO(metadata));
    }
}