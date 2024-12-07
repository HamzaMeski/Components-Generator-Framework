package com.example.backend.generator.cli;

import com.example.backend.generator.ComponentGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneratorCommand implements CommandLineRunner {
    private final ComponentGenerator componentGenerator;

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  generate <EntityName>  - Generate components for specific entity");
            System.out.println("  generate --all         - Generate components for all entities");
            return;
        }

        if (args[0].equals("generate")) {
            if (args.length < 2) {
                System.out.println("Please specify an entity name or use --all");
                return;
            }

            String entityArg = args[1];
            if (entityArg.equals("--all")) {
                System.out.println("Generating components for all entities...");
                componentGenerator.generateComponentsForAllEntities();
                System.out.println("Component generation completed successfully!");
            } else {
                System.out.println("Generating components for entity: " + entityArg);
                componentGenerator.generateComponentsForEntity(entityArg);
                System.out.println("Component generation completed successfully!");
            }
        } else {
            System.out.println("Unknown command: " + args[0]);
            System.out.println("Available commands: generate");
        }
    }
}