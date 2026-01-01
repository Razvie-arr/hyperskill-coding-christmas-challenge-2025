package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * On the lunch break you notice puzzled Mr. Frost, looking at a chessboard. "Hey, good afternoon! Have not seen you today yet. Happy New Year, by the way! I tried to organize a first chess tournament of 2026 for nutcrackers, and one of those nutjobs came claiming that he has found a new previously unseen valid solution to the Eight Queens Problem - you know, placing 8 queens on a chessboard so none of them can attack each other. This bothers me a lot."
 * <p>
 * He sets down a file with coordinates. "I need you to verify this. Count how many pairs of queens are attacking each other. A valid solution should have zero conflicts - no two queens on the same row, column, or diagonal. If there are conflicts, I need to know exactly how many pairs are problematic."
 * <p>
 * <p>
 * <b>Input format</b>: 8 lines, each containing a queen's position as row,col (both 0-7, representing positions on an 8×8 chessboard) <br>
 * <b>Output</b>: The number of queen pairs that are attacking each other. Output 0 if it's a valid solution (no conflicts).
 * <p>
 * <b>Rules for queen attacks</b>:
 * <ul>
 * <li>
 * Queens attack along rows (horizontal)
 * </li>
 * <li>
 * Queens attack along columns (vertical)
 * </li>
 * <li>
 * Queens attack along diagonals (up-left to down-right and up-right to down-left)
 * </li>
 * </ul>
 */
public class Day8 {

    @NotNull
    private final List<BoardLocation> queenLocations = new ArrayList<>();

    public void addQueenLocation(@NotNull BoardLocation location) {
        queenLocations.add(location);
    }

    public int calculateQueenCollisions() {
        int queensCollisions = 0;
        for (int i = 0; i < queenLocations.size(); i++) {
            BoardLocation currentQueenLocation = queenLocations.get(i);
            for (int j = i + 1; j < queenLocations.size(); j++) {
                BoardLocation nextQueenLocation = queenLocations.get(j);
                if (doQueensCollide(currentQueenLocation, nextQueenLocation)) {
                    queensCollisions++;
                }
            }
        }
        return queensCollisions;
    }

    private boolean doQueensCollide(@NotNull BoardLocation queenLocation1, @NotNull BoardLocation queenLocation2) {
        // abscissa collision
        if (queenLocation1.x() == queenLocation2.x()) {
            return true;
        }
        // ordinate collision
        if (queenLocation1.y() == queenLocation2.y()) {
            return true;
        }
        // diagonal collision
        return Math.abs(queenLocation2.x() - queenLocation1.x()) == Math.abs(queenLocation2.y() - queenLocation1.y());
    }

    public record BoardLocation(int x, int y) {
    }

}

class Day8Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day8_dataset.txt");
        Day8 day8 = new Day8();
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            day8.addQueenLocation(new Day8.BoardLocation(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        System.out.println(day8.calculateQueenCollisions());
    }

}
