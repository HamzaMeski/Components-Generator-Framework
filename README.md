# Spring Boot Component Generator Framework

A powerful framework built on top of Spring Boot that automatically generates CRUD components for your entities. This framework significantly reduces boilerplate code by generating controllers, services, repositories, mappers, and DTOs based on your entity definitions.

## 🚀 Features

- **Automatic Component Generation**: Generate all necessary CRUD components from entity classes
- **Smart Validation**: Mirrors entity validations in DTOs
- **Relationship Handling**: Proper handling of entity relationships in generated components
- **Type-Safe**: Generates type-safe components with proper imports
- **Customizable**: Easy to extend and customize for specific needs

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6.x or higher
- Spring Boot 3.x

## 🛠️ Installation

1. Clone the repository:
```bash
git clone https://github.com/HamzaMeski/Components-Generator-Framework.git
```
## 📖 Usage

1. **Define Your Entities**
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Name is required")
    private String name;
    
    @Min(value = 0, message = "Age must be positive")
    private int age;
    
    @ManyToOne
    private RelatedEntity relatedEntity;
}
```
2. **Generate Components**

Run the following Maven command to generate components for all entities:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="generate --all"
```

Or for a specific entity:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="generate --entity YourEntity"
```
3. **Generated Structure**
```txt
For each entity, the framework generates:
components/
└── yourentity/
    ├── controller/
    │   └── YourEntityController.java
    ├── service/
    │   └── YourEntityService.java
    ├── repository/
    │   └── YourEntityRepository.java
    ├── mapper/
    │   └── YourEntityMapper.java
    └── dto/
        ├── request/
        │   ├── CreateYourEntityDTO.java
        │   └── UpdateYourEntityDTO.java
        └── response/
            └── YourEntityResponseDTO.java
```
## 🔧 Generated Components

### 1. Controller
- REST endpoints for CRUD operations
- Proper request/response handling
- Validation support

### 2. Service
- Business logic implementation
- Transaction management
- Error handling

### 3. Repository
- JPA repository for database operations
- Custom query methods

### 4. Mapper
- DTO to Entity conversion
- Entity to DTO conversion
- Relationship handling

### 5. DTOs
- Create DTO with validation
- Update DTO with validation
- Response DTO with nested objects

## 🎯 Best Practices

1. **Entity Definition**
   - Use proper JPA annotations
   - Include validation constraints
   - Define clear relationships

2. **Package Structure**
   - Keep entities in the `entities` package
   - Generated components will be in `components/<entity-name>`

3. **Customization**
   - Extend generated classes for custom logic
   - Override methods when needed
   - Add custom endpoints in controller

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
