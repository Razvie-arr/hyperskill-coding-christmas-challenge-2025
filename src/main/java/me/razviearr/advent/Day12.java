package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Today you came into an empty office. Just yesterday it was lively, filled with reindeers, elves, gingerbread people, snow folk, music was too loud to concentrate on the task and fireworks were fired outside every other hour. Now it's just completely silent. Not a living soul. Today is the last day of your internship. You log on to your computer. There's just one unread email. From your team lead Mr. Frost. Download the attachment to see what he wrote.
 */
public class Day12 {

    @NotNull
    private final String message;

    public Day12(@NotNull String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }

}

class Day12Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day12_dataset.txt");
        Day12 day12 = new Day12(lines.getFirst());
        day12.printMessage();
    }

}
