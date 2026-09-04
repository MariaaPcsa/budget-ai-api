package com.budgetai.domain.repository;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository
        extends JpaRepository<Expense, UUID> {

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt >= :start
            AND e.createdAt <= :end
            """)
    BigDecimal getTodayTotal(
            UUID userId,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Expense> findAllByUserId(UUID userId);

    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);

        long countByUserIsNull();

        @Modifying
        @Query("UPDATE Expense e SET e.user = :user WHERE e.user IS NULL")
        void assignUnownedExpenses(User user);
}