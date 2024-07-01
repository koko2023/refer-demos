package com.bear.hospital.service.serviceImpl;

import com.bear.hospital.model.ChatbotRequest;
import com.bear.hospital.model.ChatbotResponse;
import com.bear.hospital.service.AskAIBotService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AskAIBotServiceImpl implements AskAIBotService {

    @Value("${chatbot.api.url}")
    private String apiUrl;

    @Value("${chatbot.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ChatbotResponse ask(ChatbotRequest chatbotRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("inputs", new HashMap<>());
        payload.put("query", chatbotRequest.getQuery());
        payload.put("response_mode", "streaming");
        payload.put("conversation_id", "");
        payload.put("user", "abc-123");

        Map<String, String> file = new HashMap<>();
        file.put("type", "image");
        file.put("transfer_method", "remote_url");
        file.put("url", "https://cloud.dify.ai/logo/logo-site.png");

        payload.put("files", new Map[]{file});

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        ChatbotResponse chatbotResponse = new ChatbotResponse();
        Map<String, String> answer = new HashMap<>();

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            if (responseBody != null) {
                try {
                    String fullAnswer = parseStreamingResponse(responseBody);
                    answer.put("answer", fullAnswer);
                } catch (IOException e) {
                    System.out.println("JSON decode error: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Request failed with status code " + response.getStatusCode());
            if (response.hasBody()) {
                System.out.println(response.getBody());
            }
        }

        chatbotResponse.setAnswer(answer);
        return chatbotResponse;
    }

    private String parseStreamingResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder fullAnswer = new StringBuilder();

        for (String line : responseBody.split("\n")) {
            if (line.startsWith("data:")) {
                String jsonData = line.substring(5).trim();
                JsonNode node = mapper.readTree(jsonData);
                if (node.has("answer")) {
                    fullAnswer.append(node.get("answer").asText());
                }
            }
        }

        return fullAnswer.toString();
    }
}
