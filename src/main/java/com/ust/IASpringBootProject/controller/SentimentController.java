package com.ust.IASpringBootProject.controller;

import com.ust.IASpringBootProject.model.SentimentRequest;
import com.ust.IASpringBootProject.model.SentimentResult;
import com.ust.IASpringBootProject.service.SentimentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentController {
    private final SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @PostMapping("/analyze")
    public SentimentResult analyzeSentiment(@RequestBody SentimentRequest request) {
        return sentimentService.analyzeSentiment(request.getComment());
    }
}
