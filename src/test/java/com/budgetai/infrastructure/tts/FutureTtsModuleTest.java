package com.budgetai.infrastructure.tts;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FutureTtsModuleTest {

    @Test
    void deveGerarArrayDeAudio() {

        // cria módulo
        FutureTtsModule module =
                new FutureTtsModule();

        // executa método
        byte[] result =
                module.generateAudio(
                        "Olá mundo"
                );

        // valida retorno
        assertNotNull(result);

        assertEquals(0, result.length);
    }
}