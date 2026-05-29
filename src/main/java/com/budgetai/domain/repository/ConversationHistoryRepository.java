package com.budgetai.domain.repository;

import com.budgetai.domain.entity.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationHistoryRepository
        extends JpaRepository<ConversationHistory, UUID> {
}