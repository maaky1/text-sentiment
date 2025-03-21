package com.github.maaky1.text_sentiment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PreprocessingService {
    private final List<String> stopwordDict;
    private final Map<String, String> slangDict;
    private final List<String> rootDict;
    private final List<String> PREFIXES = List.of("me", "mem", "men", "meng", "meny", "be", "ber", "di", "ke", "se", "ter", "pe", "per");
    private final List<String> SUFFIXES = List.of("kan", "an", "i", "lah", "kah", "tah", "pun", "nya");

    public PreprocessingService(InitService initService) {
        this.stopwordDict = initService.getStopwordDict();
        this.slangDict = initService.getSlangDict();
        this.rootDict = initService.getRootDict();
    }

    public List<String> preprocessText(String text) {
        // 1. Case Folding (Ubah ke huruf kecil)
        text = text.toLowerCase();

        // 2. Remove Punctuation (Hapus Tanda Baca)
        text = text.replaceAll("[^a-zA-Z\\s]", "");

        // 3. Tokenization (Pecah teks jadi kata)
        List<String> words = Arrays.asList(text.split("\\s+"));

        // 4. Stopword Removal (Hapus Kata Umum yang Tidak Berarti)
        words = words.stream()
                .filter(word -> !stopwordDict.contains(word))
                .toList();

        // 5. Normalize Slang Words (Ubah slang ke kata baku)
        words = words.stream()
                .map(word -> slangDict.getOrDefault(word, word))
                .toList();

        // 6. Remove Repeated Characters (Hapus huruf berulang)
        words = words.stream()
                .map(this::removeRepeatedCharacters)
                .toList();

        // 7. Stemming (Ubah kata ke bentuk dasar)
        words = words.stream()
                .map(this::stemWord)
                .toList();

        // 8. Emoji Handling (Hapus/ubah emoji)

        // 9. Remove Unicode (Hapus karakter aneh)

        // 10. Join Sentence (Gabungkan kembali teks)
        String processedText = String.join(" ", words);
        log.info("Preprocessing text: " + processedText);

        return words;
    }

    private String removeRepeatedCharacters(String word) {
        return word.replaceAll("(.)\\1+", "$1");
    }

    private String stemWord(String word) {
        // Jika kata sudah ada di kamus root words, langsung return
        if (rootDict.contains(word)) {
            return word;
        }

        // 1. Cek dan hapus imbuhan awalan (prefix)
        String wordWithoutPrefix = removePrefix(word);
        if (rootDict.contains(wordWithoutPrefix)) {
            return wordWithoutPrefix;
        }

        // 2. Cek dan hapus imbuhan akhiran (suffix)
        String wordWithoutSuffix = removeSuffix(word);
        if (rootDict.contains(wordWithoutSuffix)) {
            return wordWithoutSuffix;
        }

        // 3. Jika kata masih tidak ditemukan, coba hapus awalan dan akhiran sekaligus
        String wordWithoutPrefixSuffix = removePrefix(removeSuffix(word));
        if (rootDict.contains(wordWithoutPrefixSuffix)) {
            return wordWithoutPrefixSuffix;
        }

        // 4. Jika tetap tidak ditemukan, kembalikan kata asli
        return word;
    }

    private String removePrefix(String word) {
        for (String prefix : PREFIXES) {
            if (word.startsWith(prefix)) {
                String candidate = word.substring(prefix.length());
                if (rootDict.contains(candidate)) {
                    return candidate;
                }
            }
        }
        return word;
    }

    private String removeSuffix(String word) {
        for (String suffix : SUFFIXES) {
            if (word.endsWith(suffix)) {
                String candidate = word.substring(0, word.length() - suffix.length());
                if (rootDict.contains(candidate)) {
                    return candidate;
                }
            }
        }
        return word;
    }
}