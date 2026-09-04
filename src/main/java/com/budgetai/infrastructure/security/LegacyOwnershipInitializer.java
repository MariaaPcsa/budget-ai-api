package com.budgetai.infrastructure.security;

import com.budgetai.domain.entity.User;
import com.budgetai.domain.repository.ConversationRepository;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LegacyOwnershipInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ConversationRepository conversationRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.initial-admin.email:}")
    private String initialAdminEmail;

    @Value("${app.initial-admin.password:}")
    private String initialAdminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (expenseRepository.countByUserIsNull() == 0 && conversationRepository.countByUserIsNull() == 0) {
            return;
        }

        if (initialAdminEmail.isBlank() || initialAdminPassword.isBlank()) {
            throw new IllegalStateException("Dados legados exigem INITIAL_ADMIN_EMAIL e INITIAL_ADMIN_PASSWORD");
        }

        String email = initialAdminEmail.trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .passwordHash(passwordEncoder.encode(initialAdminPassword))
                        .createdAt(LocalDateTime.now())
                        .build()));

        expenseRepository.assignUnownedExpenses(user);
        conversationRepository.assignUnownedConversations(user);
    }
}