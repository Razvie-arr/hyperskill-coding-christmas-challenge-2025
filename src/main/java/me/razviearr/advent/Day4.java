package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * On the fourth day of coding my team lead sent to me... Four dining elves,
 * <p>
 * Three security rules, two pointers, and a nasty bug in a production tree!
 * <p>
 * Mr. Frost looks exhausted. "We have a situation in the cafeteria. Four elves - Jingle, Sparkle, Tinsel, and Holly - are sitting around a circular table trying to eat lunch. There are four forks on the table, one between each pair of elves. Each elf needs TWO forks to eat (the one on their left AND the one on their right)."
 * <p>
 * He pulls up a log on his screen. "The problem is, they keep trying to grab forks that other elves are already using. Every time an elf attempts to pick up a fork that's currently held by someone else, it causes a 'contention' - they have to wait, complain, and it slows everything down."
 * <p>
 * "I need you to analyze this log and count how many contentions occurred during lunch. Help me figure out how bad the problem really is."
 * <p>
 * Input: logs of actions separated by newline. Each log contains name of the elf, action (pick or release) and an id of a fork, all separated by comma. Example: Jingle,pick,3
 * Output: Total number of contentions (attempts to pick up a fork that's already held by another elf).
 * <p>
 * Order of elves and forks around the table:
 * <p>
 * Jingle - Fork_1 - Sparkle - Fork_2 - Tinsel - Fork_3 - Holly - Fork_4 - (back to) Jingle
 */

public class Day4 {

    @NotNull
    private final Map<Integer, Fork> forkIdToFork = new HashMap<>();
    @NotNull
    private final List<Log> logs;

    public Day4(@NotNull List<Log> logs) {
        this.logs = logs;

        forkIdToFork.put(1, new Fork());
        forkIdToFork.put(2, new Fork());
        forkIdToFork.put(3, new Fork());
        forkIdToFork.put(4, new Fork());
    }

    public int calculateContentions() {
        int contentions = 0;
        for (var log : logs) {
            Fork fork = forkIdToFork.get(log.id);

            if (log.action == Action.PICK) {
                if (fork.isOccupied()) {
                    contentions++;
                } else {
                    fork.occupy();
                }
            } else {
                fork.free();
            }
        }
        return contentions;
    }

    private static class Fork {

        private boolean occupied = false;

        public boolean isOccupied() {
            return occupied;
        }

        public void occupy() {
            occupied = true;
        }

        public void free() {
            occupied = false;
        }

    }

    public enum Action {
        PICK,
        RELEASE
    }

    public record Log(@NotNull Action action, int id) {
    }

}

class Day4Runner {

    public static void main(String[] args) {
        List<String> logLines = InputReader.readLines("inputs/day4_dataset.txt");
        List<Day4.Log> logs = new ArrayList<>();
        for (var line : logLines) {
            String[] split = line.split(",");
            // ignoring elf name
            Day4.Action action = Day4.Action.valueOf(split[1].toUpperCase());
            int id = Integer.parseInt(split[2]);
            logs.add(new Day4.Log(action, id));
        }
        Day4 day4 = new Day4(logs);
        System.out.println(day4.calculateContentions());
    }
    
}
