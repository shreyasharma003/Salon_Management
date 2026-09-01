package com.salon.dashboard_service.service.impl;

import com.salon.dashboard_service.dto.enums.InventoryEventType;
import com.salon.dashboard_service.dto.eventDto.InventoryEvent;
import com.salon.dashboard_service.dto.outDto.InventoryDashboardItemResponse;
import com.salon.dashboard_service.dto.outDto.InventoryDashboardResponse;
import com.salon.dashboard_service.entity.InventoryDashboardItem;
import com.salon.dashboard_service.repository.InventoryDashboardItemRepository;
import com.salon.dashboard_service.service.InventoryDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryDashboardServiceImpl
        implements InventoryDashboardService {

    private final InventoryDashboardItemRepository repository;

    @Override
    public void processInventoryEvent(InventoryEvent event) {

        InventoryDashboardItem item =
                repository.findBySku(event.getSku())
                        .orElseGet(() -> {

                            InventoryDashboardItem newItem =
                                    new InventoryDashboardItem();

                            newItem.setSku(event.getSku());
                            newItem.setProductName(event.getProductName());

                            return newItem;
                        });

        item.setProductName(event.getProductName());
        item.setQuantity(event.getQuantity());
        item.setEventType(event.getEventType());
        item.setLastUpdated(event.getTimestamp());

        repository.save(item);
    }

    @Override
    public InventoryDashboardResponse getInventoryDashboard() {

        List<InventoryDashboardItem> items =
                repository.findAll();

        long lowStockItems =
                items.stream()
                        .filter(item ->
                                item.getEventType() == InventoryEventType.INVENTORY_LOW)
                        .count();

        long outOfStockItems =
                items.stream()
                        .filter(item ->
                                item.getEventType() == InventoryEventType.INVENTORY_OUT_OF_STOCK)
                        .count();

        List<InventoryDashboardItemResponse> itemResponses =
                items.stream()
                        .map(item ->
                                new InventoryDashboardItemResponse(
                                        item.getSku(),
                                        item.getProductName(),
                                        item.getQuantity(),
                                        item.getEventType().name()
                                )
                        )
                        .toList();

        return new InventoryDashboardResponse(
                lowStockItems,
                outOfStockItems,
                itemResponses
        );
    }
}