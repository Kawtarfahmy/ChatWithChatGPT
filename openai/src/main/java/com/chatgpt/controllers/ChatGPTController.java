package com.chatgpt.controllers;

import com.chatgpt.dto.ChatGPTResponseDTO;
import com.chatgpt.dto.Choice;
import com.chatgpt.dto.QuestionDTO;
import com.chatgpt.dto.ResponseDTO;
import com.chatgpt.services.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-gpt")
@AllArgsConstructor
public class ChatGPTController {


    private ChatGptService chatGptService;


    @GetMapping
    public ChatGPTResponseDTO getAnswerFromQuestion(@RequestBody QuestionDTO questionDTO) throws Exception {

        return chatGptService.getAnswer(questionDTO.getQuestion());

    }

    @PostMapping("/csv")
    public List<Choice> getShortAnswerFromQuestion(@RequestBody QuestionDTO questionDTO) throws Exception {

        ChatGPTResponseDTO chatGPTResponseDTO = chatGptService.getAnswer(questionDTO.getQuestion());



        chatGptService.writeToCSV(
                new ResponseDTO(questionDTO.getQuestion(),
                        chatGPTResponseDTO.getChoices().get(0).getText())
        );


        return chatGPTResponseDTO.getChoices();
    }

    @GetMapping("/csv")
    public List<String> readCsvFile() throws Exception {
        return chatGptService.readCSV();
    }


}
