package com.salon.service_catalog_service.repository;

import com.salon.service_catalog_service.modal.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    boolean existsByNameIgnoreCaseAndCategoryId(
            String name,
            Long categoryId
    );

    boolean existsByNameIgnoreCaseAndCategoryIdAndIdNot(
            String name,
            Long categoryId,
            Long id
    );

    Page<Service> findByCategoryId(
            Long categoryId,
            Pageable pageable
    );

    Page<Service> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description,
            Pageable pageable
    );
}
