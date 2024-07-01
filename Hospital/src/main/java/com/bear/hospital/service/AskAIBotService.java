package com.bear.hospital.service;

import com.bear.hospital.model.ChatbotRequest;
import com.bear.hospital.model.ChatbotResponse;

public interface AskAIBotService {
    public ChatbotResponse ask(ChatbotRequest chatbotRequest);
}
