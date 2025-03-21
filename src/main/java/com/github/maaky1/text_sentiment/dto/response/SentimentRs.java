package com.github.maaky1.text_sentiment.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SentimentRs {
    private int positiveWordCount;
    private int negativeWordCount;
    private int totalWordCount;
    private double sentimentScore;
    private String sentiment;
}
