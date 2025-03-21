package com.github.maaky1.text_sentiment.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Slf4j
@Service
public class InitService {
    private static final Map<String, String> URLS = Map.of(
            "negative", "https://raw.githubusercontent.com/ramaprakoso/analisis-sentimen/refs/heads/master/kamus/negatif_ta2.txt",
            "positive", "https://raw.githubusercontent.com/ramaprakoso/analisis-sentimen/refs/heads/master/kamus/positif_ta2.txt",
            "stopwords", "https://raw.githubusercontent.com/louisowen6/NLP_bahasa_resources/refs/heads/master/combined_stop_words.txt",
            "slang", "https://raw.githubusercontent.com/ramaprakoso/analisis-sentimen/refs/heads/master/kamus/kbba.txt",
            "root", "https://raw.githubusercontent.com/louisowen6/NLP_bahasa_resources/master/combined_root_words.txt"
    );

    @Getter
    private List<String> negativeDict = new ArrayList<>();
    @Getter
    private List<String> positiveDict = new ArrayList<>();
    @Getter
    private List<String> stopwordDict = new ArrayList<>();
    @Getter
    private Map<String, String> slangDict = new HashMap<>();
    @Getter
    private List<String> rootDict = new ArrayList<>();

    @PostConstruct
    public void initService() {
        log.info("==========[Load Dictionary Words Start]==========");
        negativeDict = loadList(URLS.get("negative"));
        positiveDict = loadList(URLS.get("positive"));
        stopwordDict = loadList(URLS.get("stopwords"));
        slangDict = loadSlangDict(URLS.get("slang"));
        rootDict = loadList(URLS.get("root"));
        log.info("Negative Words: {},Positive Words: {}, Stopwords: {}, Slang Words: {}, Root Words: {}",
                negativeDict.size(), positiveDict.size(), stopwordDict.size(), slangDict.size(), rootDict.size());
        log.info("==========[Load Dictionary Words End]==========");
    }

    private List<String> loadList(String url) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            return reader.lines().map(String::trim).toList();
        } catch (Exception e) {
            log.error("Error loading {}: {}", url, e.getMessage());
            return Collections.emptyList();
        }
    }

    private Map<String, String> loadSlangDict(String url) {
        Map<String, String> slangMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            reader.lines()
                    .map(line -> line.split("\t"))
                    .filter(parts -> parts.length == 2)
                    .forEach(parts -> slangMap.put(parts[0].trim(), parts[1].trim()));
        } catch (Exception e) {
            log.error("Error loading slang words: {}", e.getMessage());
        }
        return slangMap;
    }
}
