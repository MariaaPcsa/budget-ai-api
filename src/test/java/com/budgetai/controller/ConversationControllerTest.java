package com.budgetai.controller;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.application.usecase.GetConversationHistoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConversationController.class)
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetConversationHistoryUseCase useCase;

    @Test
    void shouldReturnConversationHistory() throws Exception {

        ConversationResponseDTO dto =
                new ConversationResponseDTO(
                        1L,
                        "Quanto gastei hoje?",
                        "Você gastou R$ 200",
                        LocalDateTime.now()
                );

        when(useCase.execute())
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
