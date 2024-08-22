package com.ust.IASpringBootProject.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.IASpringBootProject.model.SentimentResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
@Service
public class SentimentService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api-inference.huggingface.co/models/distilbert-base-uncased-finetuned-sst-2-english";
    private final String apiKey = "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";

    public SentimentResult analyzeSentiment(String comment) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{ \"inputs\": \"" + comment + "\" }";
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // We make the request to the Hugging Face API
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        // Print the JSON response in the console
        System.out.println("Response JSON: " + response.getBody());

        // Process the JSON response to map it to an array of arrays of SentimentResult
        ObjectMapper objectMapper = new ObjectMapper();
        SentimentResult[][] results = null;
        try {
            results = objectMapper.readValue(response.getBody(), SentimentResult[][].class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ensure that we have results and filter the most relevant result
        if (results != null && results.length > 0) {
            return Arrays.stream(results[0])
                    .max(Comparator.comparing(SentimentResult::getScore))
                    .orElse(null);
        }
        return null;
    }

}

