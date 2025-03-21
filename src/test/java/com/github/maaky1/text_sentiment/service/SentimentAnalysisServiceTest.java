package com.github.maaky1.text_sentiment.service;

import com.github.maaky1.text_sentiment.dto.request.SentimentRq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SentimentAnalysisServiceTest {
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Test
    void analyzeText() {
        sentimentAnalysisService.analyzeText(new SentimentRq().setText("Saya sangat senang dengan produk ini, tapi sangat mahal."));
    }
}