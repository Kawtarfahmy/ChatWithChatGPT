package com.chatgpt.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatGPTResponseDTO {
    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choice> choices;
}
