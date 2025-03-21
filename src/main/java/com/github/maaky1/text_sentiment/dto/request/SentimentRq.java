package com.github.maaky1.text_sentiment.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SentimentRq {
    private String text;
}
