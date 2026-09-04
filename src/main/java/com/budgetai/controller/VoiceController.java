package com.budgetai.controller;

import com.budgetai.application.dto.VoiceResponseDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.VoiceChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class VoiceController {

    private final VoiceChatUseCase voiceChatUseCase;
        private final CurrentUserProvider currentUserProvider;

    @PostMapping(
            value = "/voice",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<VoiceResponseDTO> voiceChat(
            @RequestParam("file") MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new VoiceResponseDTO(
                            null,
                            "Arquivo de áudio não pode estar vazio"
                    ));
        }

        VoiceResponseDTO response = voiceChatUseCase.execute(currentUserProvider.currentUserId(), file);

        return ResponseEntity.ok(response);
    }
}
