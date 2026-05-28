package com.budgetai.controller;

import com.budgetai.application.dto.ChatRequestDTO;
import com.budgetai.application.dto.ChatResponseDTO;
import com.budgetai.application.service.AssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(
            @Valid @RequestBody ChatRequestDTO request
    ) {

        String response = assistantService.processMessage(request.message());

        return ResponseEntity.ok(
                new ChatResponseDTO(
                        "success",
                        response
                )
        );
    }
}