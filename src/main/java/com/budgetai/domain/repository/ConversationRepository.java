package com.budgetai.domain.repository;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long> {

        List<Conversation> findAllByUserIdOrderByCreatedAtAsc(UUID userId);

        long countByUserIsNull();

        @Modifying
        @Query("UPDATE Conversation c SET c.user = :user WHERE c.user IS NULL")
        void assignUnownedConversations(User user);
}
