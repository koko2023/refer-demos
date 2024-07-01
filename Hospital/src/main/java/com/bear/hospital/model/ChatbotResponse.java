package com.bear.hospital.model;

import com.bear.hospital.pojo.Department;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotResponse {

    private Map<String, String> answer;

}
