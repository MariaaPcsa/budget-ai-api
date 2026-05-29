package com.budgetai.domain.repository;

import com.budgetai.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ExpenseRepository
        extends JpaRepository<Expense, UUID> {

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.createdAt >= :start
            AND e.createdAt <= :end
            """)
    BigDecimal getTodayTotal(
            LocalDateTime start,
            LocalDateTime end
    );
}