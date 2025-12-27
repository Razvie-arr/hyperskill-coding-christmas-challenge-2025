package me.razviearr.advent.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class InputReader {

    /**
     * Reads a file from src/main/resources and returns it as a List of Strings.
     */
    public static List<String> readLines(@NotNull String filename) {
        try (InputStream is = InputReader.class.getClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found in resources: " + filename);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input file: " + filename, e);
        }
    }

    /**
     * Returns the entire file as a single String (useful for grid-based puzzles).
     */
    public static String readString(@NotNull String filename) {
        return String.join("\n", readLines(filename));
    }

    /**
     * Helper for puzzles that are just a list of numbers.
     */
    public static List<Integer> readInts(@NotNull String filename) {
        return readLines(filename).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}