package com.github.maaky1.text_sentiment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PreprocessingServiceTest {
    @Autowired
    private PreprocessingService preprocessingService;

    @Test
    void preprocess() {
        preprocessingService.preprocessText("Baguuuuusss adalah apa dia akhir bgt!!! bs berdiri walau terkadang tertawa dan meskipun begitu begini tertabrak, . ! <>323 $%^&*()(*&^%$ ehh tp gw gpp loh");
    }
}