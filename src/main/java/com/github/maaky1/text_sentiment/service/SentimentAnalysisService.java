package com.github.maaky1.text_sentiment.service;

import com.github.maaky1.text_sentiment.dto.request.SentimentRq;
import com.github.maaky1.text_sentiment.dto.response.SentimentRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SentimentAnalysisService {
    private final List<String> negDict;
    private final List<String> posDict;
    private final PreprocessingService preprocessingService;

    @Autowired
    public SentimentAnalysisService(InitService initService, PreprocessingService preprocessingService) {
        this.negDict = initService.getNegativeDict();
        this.posDict = initService.getPositiveDict();
        this.preprocessingService = preprocessingService;
    }

    public SentimentRs analyzeText(SentimentRq request) {
        // 1. Preprocessing Text
        List<String> words = preprocessingService.preprocessText(request.getText());

        // 2. Count positive and negative word
        int posCount = 0;
        int negCount = 0;
        for (String word : words) {
            if (negDict.contains(word)) {
                negCount++;
            } else if (posDict.contains(word)) {
                posCount++;
            }
        }

        // 3. Count total word
        int totalWords = words.size();

        // 4. Calculate Normalized Sentiment Score
        double sentimentScore = (double) (posCount - negCount) / totalWords;

        // 5. Return result
        return new SentimentRs()
                .setPositiveWordCount(posCount)
                .setNegativeWordCount(negCount)
                .setTotalWordCount(totalWords)
                .setSentimentScore(sentimentScore)
                .setSentiment(
                        sentimentScore == 0 ? "Sentiment Neutral" : sentimentScore > 0 ? "Sentiment Positive" : "Sentiment Negative"
                );
    }
}
