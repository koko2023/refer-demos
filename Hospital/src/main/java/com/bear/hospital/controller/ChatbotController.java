package com.bear.hospital.controller;

import com.bear.hospital.model.ChatbotRequest;
import com.bear.hospital.model.ChatbotResponse;
import com.bear.hospital.service.AskAIBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "http://localhost:4202")
public class ChatbotController {

    private List<String> userInputs = new ArrayList<>();

    @Autowired
    private AskAIBotService askAIBotService;

    @PostMapping("/ask")
    public ResponseEntity<ChatbotResponse> askAIBot(ChatbotRequest chatbotRequest) {
        return ResponseEntity.ok(askAIBotService.ask(chatbotRequest));
    }

    @PostMapping("/collect")
    public ResponseEntity<ChatbotResponse> collectUserInput(@RequestParam String input) {
        try {
            input = URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        userInputs.add(input);

        if (userInputs.size() == 1) {
            ChatbotResponse response = new ChatbotResponse();
            response.setAnswer(Map.of("message", "还有其他症状吗？发热，咳嗽，头晕"));
            return ResponseEntity.ok(response);
        } else if (userInputs.size() == 2) {
            ChatbotRequest chatbotRequest = new ChatbotRequest();
            chatbotRequest.setQuery(String.join(" ", userInputs));
            System.out.println("res: " + chatbotRequest + " " + userInputs);
            userInputs.clear();
            return ResponseEntity.ok(askAIBotService.ask(chatbotRequest));
        }

        return ResponseEntity.ok(new ChatbotResponse());
    }


    @PostMapping("/collect1")
    public ResponseEntity<ChatbotResponse> collectUserInput2(String input) {
        userInputs.add(input);

        if (userInputs.size() == 1) {
            ChatbotResponse response = new ChatbotResponse();
            response.setAnswer(Map.of("message", "还有其他症状吗？发热，咳嗽，头晕"));
            return ResponseEntity.ok(response);
        } else if (userInputs.size() == 2) {
                ChatbotRequest chatbotRequest = new ChatbotRequest();
                chatbotRequest.setQuery(String.join(",", userInputs));
                userInputs.clear();
                return ResponseEntity.ok(askAIBotService.ask(chatbotRequest));
        }

        return ResponseEntity.ok(new ChatbotResponse());
    }
}
