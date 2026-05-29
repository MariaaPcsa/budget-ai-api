package com.budgetai.controller;

import com.budgetai.application.usecase.GenerateSpeechUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tts")
@RequiredArgsConstructor
public class TtsController {

    private final GenerateSpeechUseCase generateSpeechUseCase;

    @PostMapping(
            produces = "audio/mpeg"
    )
    public ResponseEntity<byte[]> generateAudio(
            @RequestParam String text
    ) {

        byte[] audio = generateSpeechUseCase.execute(text);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=audio.mp3"
                )
                .contentType(MediaType.valueOf("audio/mpeg"))
                .contentLength(audio.length)
                .body(audio);
    }
}