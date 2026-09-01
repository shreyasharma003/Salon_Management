package com.salon.dashboard_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer_dashboard_summary")
@Getter
@Setter
@NoArgsConstructor
public class CustomerDashboardSummary {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private long totalCustomers;

    @Column(nullable = false)
    private long customersToday;

    @Column(nullable = false)
    private long customersThisMonth;

    @Column(nullable = false)
    private LocalDate summaryDate;
}