package com.chatgpt.services;

import com.chatgpt.dto.ChatGPTResponseDTO;
import com.chatgpt.dto.ResponseDTO;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGptService {


    private final RestTemplate restTemplate;

    public ChatGptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${chatgpt.SecretKey}")
    private String chatSecretKey;

    @Value("${chatgpt.Url}")
    private String apiURL;

    private static final String FILE_NAME = "data-storage/response_data.csv";





    public ChatGPTResponseDTO getAnswer(String question){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+chatSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject request = new JSONObject();
        request.put("model","text-davinci-003");
        request.put("prompt",question);
        request.put("max_tokens",4000);
        request.put("temperature",1.0);


        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);


        return restTemplate.exchange(apiURL, HttpMethod.POST, entity, ChatGPTResponseDTO.class).getBody();
    }




    public void writeToCSV(ResponseDTO responseDTO) {
        try {

            FileWriter outputfile = new FileWriter(FILE_NAME,true);

            CSVWriter writer = new CSVWriter(outputfile);

            FileInputStream fis = new FileInputStream(FILE_NAME);

            if(fis.available()==0)
                writer.writeNext(new String[]{"Question","Answer"});



            String[] data = {responseDTO.getQuestion(),responseDTO.getAnswer().replace("\n","")};


            writer.writeNext(data);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> readCSV(){
        File file = new File(FILE_NAME);
        String line = "";
        List<String> csv_data = new ArrayList<>();
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] data = line.split(",");    // use comma as separator
                System.out.println("- [Question=" + data[0] + ", Answer=" + data[1]+"]");
                csv_data.add("- [Question=" + data[0] + ", Answer=" + data[1]+"]");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return csv_data;

    }

}
