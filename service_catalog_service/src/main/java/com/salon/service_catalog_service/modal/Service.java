package com.salon.service_catalog_service.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "services",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_service_category_name",
                    columnNames = {"category_id","name"}
            )
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length =500)
    private String description;

    @Column(nullable = false , precision = 10 , scale = 2)
    private BigDecimal price;

    @Column(nullable = false , name = "duration_minutes")
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id" , nullable = false)
    private ServiceCategory category;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
