package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Mr. Frost walks in carrying sheet music and looking frustrated. "Here you are. We're organizing the winter festival's drumming performance, and there is a huge problem. We have hired ten little drummers, and each one has practiced their own rhythm pattern - sequences of beats and rests. They are not synchronised! Imagine what a tragedy it will be, people are gonna laugh, Mrs. Frost won't be happy. If only they could play at least one little sequence together..."
 * <p>
 * He spreads out the sheet music. "I need to find the longest contiguous rhythm pattern that appears in ALL ten drummers' sequences. That's the only part they can all play together without relearning their parts. You will help me, right?"
 * <p>
 * <b>Input format</b>: 10 lines, each containing a comma-separated sequence of beats (1 = hit, 0 = rest).
 * <p>
 * <b>Output</b>: The length of the longest contiguous sequence that appears in all 10 drummers' patterns.
 */
public class Day10 {

    @NotNull
    private final List<String> drummersBeats = new ArrayList<>();

    public void addDrummerBeats(@NotNull String drummerBeats) {
        this.drummersBeats.add(drummerBeats);
    }

    public int findLongestSynchronizedSequence() {
        int maxPatternLength = 0;
        String mainDrummerBeats = drummersBeats.getFirst();
        int iterations = 0;
        for (int leftCursor = 0; leftCursor < mainDrummerBeats.length(); leftCursor++) {
            for (int rightCursor = leftCursor + maxPatternLength; rightCursor <= mainDrummerBeats.length(); rightCursor++) {
                iterations++;
                String pattern = mainDrummerBeats.substring(leftCursor, rightCursor);
                if (existsInAllOtherDrummers(pattern)) {
                    maxPatternLength = pattern.length();
                } else {
                    break;
                }
            }
        }
        System.out.println("Iterations: " + iterations);
        return maxPatternLength;
    }

    private boolean existsInAllOtherDrummers(@NotNull String pattern) {
        for (int i = 1; i < drummersBeats.size(); i++) {
            if (!drummersBeats.get(i).contains(pattern)) {
                return false;
            }
        }
        return true;
    }

}

class Day10Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day10_dataset.txt");
        Day10 day10 = new Day10();
        for (String line : lines) {
            if (line.isBlank()) continue;
            String withoutCommas = line.replaceAll(",", "");
            day10.addDrummerBeats(withoutCommas);
        }
        System.out.println(day10.findLongestSynchronizedSequence());
    }

}
