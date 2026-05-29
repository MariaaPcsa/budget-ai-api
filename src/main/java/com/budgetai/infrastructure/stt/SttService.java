package com.budgetai.infrastructure.stt;

import org.springframework.web.multipart.MultipartFile;

/**
 * Contrato para serviços de Speech-to-Text (STT).
 * Permite trocar implementações (OpenAI, Google, local, etc.)
 */
public interface SttService {

    /**
     * Transcreve um arquivo de áudio em texto.
     *
     * @param audioFile arquivo de áudio enviado pelo usuário
     * @return texto transcrito
     */
    String transcribeAudio(MultipartFile audioFile);
}