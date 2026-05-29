package com.budgetai.application.usecase;

import com.budgetai.application.dto.VoiceResponseDTO;
import com.budgetai.application.service.AssistantService;
import com.budgetai.infrastructure.stt.FutureSttModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoiceChatUseCase {

    private final FutureSttModule sttModule;
    private final AssistantService assistantService;
    private final SaveConversationUseCase saveConversationUseCase;

    public VoiceResponseDTO execute(MultipartFile audioFile) {

        if (audioFile == null || audioFile.isEmpty()) {

            throw new IllegalArgumentException(
                    "Arquivo de áudio não pode ser vazio"
            );
        }

        try {

            log.info("🎤 Iniciando processamento de voz");

            /*
             ==========================================
             1. STT → áudio para texto
             ==========================================
             */

            String transcript = sttModule.transcribeAudio(audioFile);

            if (transcript == null || transcript.isBlank()) {

                throw new RuntimeException(
                        "Transcrição vazia retornada pelo STT"
                );
            }

            log.info("📝 Transcrição: {}", transcript);

            /*
             ==========================================
             2. IA → gerar resposta
             ==========================================
             */

            String response = assistantService.processMessage(transcript);

            if (response == null || response.isBlank()) {

                throw new RuntimeException(
                        "Resposta vazia retornada pela IA"
                );
            }

            log.info("🤖 Resposta gerada com sucesso");

            /*
             ==========================================
             3. Persistir histórico
             ==========================================
             */

            saveConversationUseCase.execute(
                    transcript,
                    response
            );

            log.info("💾 Conversa salva no PostgreSQL");

            /*
             ==========================================
             4. Retorno
             ==========================================
             */

            return new VoiceResponseDTO(
                    transcript,
                    response
            );

        } catch (Exception e) {

            log.error("❌ Erro no VoiceChatUseCase", e);

            throw new RuntimeException(
                    "Falha no processamento de voz: " + e.getMessage(),
                    e
            );
        }
    }}

