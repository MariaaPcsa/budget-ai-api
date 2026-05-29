package com.budgetai.domain.repository;

import com.budgetai.domain.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {
}
