package com.salon.service_catalog_service.repository;

import com.salon.service_catalog_service.modal.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,Long> {
    boolean existsByNameIgnoreCase(String name);

    Optional<ServiceCategory> findByNameIgnoreCase(String name);
}
