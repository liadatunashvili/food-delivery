package org.example;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BookAnalysis {
    private static final Logger logger = LogManager.getLogger("BookAnalysis");
    public static void main(String[] args) throws IOException {
        String text = FileUtils.readFileToString(new File("src/main/resources/book.txt"), StandardCharsets.UTF_8);

        long uniqueWordCount = Arrays.stream(
                StringUtils
                        .split(
                                StringUtils
                                        .lowerCase(text.replaceAll("[^\\p{L}\\p{N}]+", " "))
                        )
        ).distinct().count();

        logger.info("BOOK: book.txt");
        logger.info("Unique words: {}", uniqueWordCount);

    }
}
