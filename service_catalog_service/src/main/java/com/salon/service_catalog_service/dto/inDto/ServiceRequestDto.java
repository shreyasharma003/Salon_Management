package com.salon.service_catalog_service.dto.inDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {

    @NotBlank(message = "Service name is required")
    @Size(
            max = 150,
            message = "Service name must not exceed 150 characters"
    )
    private String name;

    @Size(
            max = 1000,
            message = "Description must not exceed 1000 characters"
    )
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(
            value = "0.01",
            message = "Price must be greater than 0"
    )
    private BigDecimal price;

    @NotNull(message = "Duration is required")
    @Min(
            value = 1,
            message = "Duration must be greater than 0 minutes"
    )
    private Integer durationMinutes;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
