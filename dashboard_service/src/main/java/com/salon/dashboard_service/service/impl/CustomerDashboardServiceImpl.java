package com.salon.dashboard_service.service.impl;

import com.salon.dashboard_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.dashboard_service.dto.outDto.CustomerDashboardResponse;
import com.salon.dashboard_service.entity.CustomerDashboardSummary;
import com.salon.dashboard_service.repository.CustomerDashboardSummaryRepository;
import com.salon.dashboard_service.service.CustomerDashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerDashboardServiceImpl implements CustomerDashboardService {

    private final CustomerDashboardSummaryRepository repository;

    public CustomerDashboardServiceImpl(
            CustomerDashboardSummaryRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public void processCustomerCreatedEvent(CustomerCreatedEvent event) {

        LocalDate today = LocalDate.now();

        CustomerDashboardSummary summary =
                repository.findById(1L)
                        .orElseGet(() -> {

                            CustomerDashboardSummary newSummary =
                                    new CustomerDashboardSummary();

                            newSummary.setId(1L);
                            newSummary.setTotalCustomers(0);
                            newSummary.setCustomersToday(0);
                            newSummary.setCustomersThisMonth(0);
                            newSummary.setSummaryDate(today);

                            return newSummary;
                        });

        // New month → reset today's and this month's counts
        if (summary.getSummaryDate().getMonth() != today.getMonth()
                || summary.getSummaryDate().getYear() != today.getYear()) {

            summary.setCustomersToday(0);
            summary.setCustomersThisMonth(0);
        }

        // New day → reset today's count
        else if (!summary.getSummaryDate().equals(today)) {

            summary.setCustomersToday(0);
        }

        // Update summary date
        summary.setSummaryDate(today);

        // Increment all-time customer count
        summary.setTotalCustomers(
                summary.getTotalCustomers() + 1
        );

        // Increment today's customer count
        summary.setCustomersToday(
                summary.getCustomersToday() + 1
        );

        // Increment this month's customer count
        summary.setCustomersThisMonth(
                summary.getCustomersThisMonth() + 1
        );

        repository.save(summary);
    }

    @Override
    public CustomerDashboardResponse getCustomerDashboard() {

        CustomerDashboardSummary summary =
                repository.findById(1L)
                        .orElseGet(() -> {

                            CustomerDashboardSummary newSummary =
                                    new CustomerDashboardSummary();

                            newSummary.setId(1L);
                            newSummary.setTotalCustomers(0);
                            newSummary.setCustomersToday(0);
                            newSummary.setCustomersThisMonth(0);
                            newSummary.setSummaryDate(LocalDate.now());

                            return newSummary;
                        });

        return new CustomerDashboardResponse(
                summary.getTotalCustomers(),
                summary.getCustomersToday(),
                summary.getCustomersThisMonth()
        );
    }
}