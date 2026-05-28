//package com.budgetai.infrastructure.stt;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.mock.web.MockMultipartFile;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class FutureSttModuleTest {
//
//    @Test
//    void deveRetornarTranscricaoMock() {
//
//        SttService service = new FutureSttModule();
//
//        MockMultipartFile audio = new MockMultipartFile(
//                "file",
//                "audio.mp3",
//                "audio/mpeg",
//                "teste".getBytes()
//        );
//
//        String result = service.transcribeAudio(audio);
//
//        assertEquals("Transcrição futura", result);
//    }
//}
