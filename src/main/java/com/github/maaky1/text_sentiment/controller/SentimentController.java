package com.github.maaky1.text_sentiment.controller;

import com.github.maaky1.text_sentiment.dto.request.SentimentRq;
import com.github.maaky1.text_sentiment.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SentimentController {
    private final SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    public SentimentController(SentimentAnalysisService sentimentAnalysisService) {
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    @PostMapping("/calculate-sentiment")
    public ResponseEntity<?> calculateSentiment(@RequestBody SentimentRq payload) {
        return ResponseEntity.ok(sentimentAnalysisService.analyzeText(payload));
    }
}
