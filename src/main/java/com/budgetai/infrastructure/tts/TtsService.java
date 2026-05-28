package com.budgetai.infrastructure.tts;



public interface TtsService {
    byte[] generateAudio(String text);
}