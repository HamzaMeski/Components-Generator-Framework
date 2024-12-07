package com.example.backend.generator.scanner;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClassPathScanner {

    public static List<Class<?>> findClassesWithAnnotation(String basePackage,
                                                           Class<? extends Annotation> annotation) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(resolver);

        String pattern = "classpath*:" + basePackage.replace('.', '/') + "/**/*.class";
        Resource[] resources = resolver.getResources(pattern);

        for (Resource resource : resources) {
            MetadataReader reader = factory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(className);

            if (clazz.isAnnotationPresent(annotation)) {
                classes.add(clazz);
            }
        }

        return classes;
    }
}