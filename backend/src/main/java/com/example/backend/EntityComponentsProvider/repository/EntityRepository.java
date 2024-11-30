package com.example.backend.EntityComponentsProvider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {
}
