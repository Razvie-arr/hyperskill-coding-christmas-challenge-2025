package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Mr. Frost rushes in looking worried. "We have a situation. One of our delivery cats needs to cross the warehouse district to deliver an urgent package. The district is a 20×20 grid, and each cell has a hazard level from 0 to 3. Every time the cat passes through a cell, it loses lives equal to that cell's hazard level."
 * <p>
 * He pulls up a map. "The cat starts at the top-left corner and needs to reach the bottom-right corner. It can move up, down, left, or right - one cell at a time. The cat has 9 lives total. Find the safest path - the one that costs the fewest lives."
 * Input format: 20 lines, each containing 20 comma-separated numbers (0-3) representing hazard levels
 * Output: The minimum number of lives lost on the optimal path from top-left (0,0) to bottom-right (19,19)
 */
public class Day9 {

    private final int[][] grid = new int[20][20];
    private final int[][] liveCostsFromSource = new int[20][20];

    public void setCellValue(int row, int col, int hazardLevel) {
        grid[row][col] = hazardLevel;
    }

    public int calculateMinimumNumberOfLivesCost() {
        // Dijkstra algorithm for grid
        fillInitialLiveCosts();
        liveCostsFromSource[0][0] = 0;
        Queue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(0, 0, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            List<Neighbor> neighbors = getAllNeighbors(current.row(), current.column());
            for (Neighbor neighbor : neighbors) {
                int newLiveCost = liveCostsFromSource[current.row()][current.column()] + neighbor.hazardLevel();
                if (newLiveCost < liveCostsFromSource[neighbor.row()][neighbor.column()]) {
                    liveCostsFromSource[neighbor.row()][neighbor.column()] = newLiveCost;
                    queue.offer(new Node(neighbor.row(), neighbor.column(), newLiveCost));
                }
            }
        }
        return liveCostsFromSource[grid.length - 1][grid.length - 1];
    }

    private void fillInitialLiveCosts() {
        for (int[] liveCost : liveCostsFromSource) {
            Arrays.fill(liveCost, Integer.MAX_VALUE);
        }
    }

    @NotNull
    private List<Neighbor> getAllNeighbors(int row, int col) {
        List<Neighbor> neighbors = new ArrayList<>();
        if (row != 0) {
            neighbors.add(new Neighbor(row - 1, col, grid[row - 1][col]));
        }
        if (row != grid.length - 1) {
            neighbors.add(new Neighbor(row + 1, col, grid[row + 1][col]));
        }
        if (col != 0) {
            neighbors.add(new Neighbor(row, col - 1, grid[row][col - 1]));
        }
        if (col != grid[0].length - 1) {
            neighbors.add(new Neighbor(row, col + 1, grid[row][col + 1]));
        }
        return neighbors;
    }

}

record Node(int row, int column, int liveCostFromSource) implements Comparable<Node> {
    @Override
    public int compareTo(@NotNull Node other) {
        return Integer.compare(this.liveCostFromSource, other.liveCostFromSource);
    }
}

record Neighbor(int row, int column, int hazardLevel) {
}

class Day9Runner {

    public static void main(String[] args) {
        Day9 day9 = new Day9();

        List<String> lines = InputReader.readLines("inputs/day9_dataset.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            for (int j = 0; j < parts.length; j++) {
                day9.setCellValue(i, j, Integer.parseInt(parts[j]));
            }
        }

        System.out.println(day9.calculateMinimumNumberOfLivesCost());
    }

}
