package com.budgetai.infrastructure.stt;



import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FutureSttModuleTest {

    @Test
    void deveRetornarMensagemDeTranscricaoFutura() {

        // cria arquivo mockado
        MockMultipartFile audioFile =
                new MockMultipartFile(
                        "audio",
                        "teste.mp3",
                        "audio/mpeg",
                        "audio fake".getBytes()
                );

        // cria módulo
        FutureSttModule module =
                new FutureSttModule();

        // executa método
        String result =
                module.transcribeAudio(audioFile);

        // valida retorno
        assertEquals(
                "Transcrição futura",
                result
        );
    }
}
