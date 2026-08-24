package com.salon.service_catalog_service.dto.inDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStatusUpdateRequestDto {

    @NotNull(message = "Active status is required")
    private Boolean active;
}
