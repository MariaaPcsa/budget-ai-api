package com.budgetai.controller;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.GetConversationHistoryUseCase;
import com.budgetai.infrastructure.security.JwtAuthenticationFilter;
import com.budgetai.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ConversationController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetConversationHistoryUseCase useCase;

        @MockBean
        private CurrentUserProvider currentUserProvider;

    @Test
    void shouldReturnConversationHistory() throws Exception {

        ConversationResponseDTO dto =
                new ConversationResponseDTO(
                        1L,
                        "Quanto gastei hoje?",
                        "Você gastou R$ 200",
                        LocalDateTime.now()
                );

        UUID userId = UUID.randomUUID();
        when(currentUserProvider.currentUserId()).thenReturn(userId);
        when(useCase.execute(userId))
                .thenReturn(List.of(dto));

        mockMvc.perform(
                        get("/api/conversations")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userMessage")
                        .value("Quanto gastei hoje?"))
                .andExpect(jsonPath("$[0].aiResponse")
                        .value("Você gastou R$ 200"));
    }
}
