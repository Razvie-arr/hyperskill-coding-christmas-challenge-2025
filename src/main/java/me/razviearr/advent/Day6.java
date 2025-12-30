package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * On the sixth day of coding my team lead sent to me... Six handshakes,
 * <p>
 * Five-pointed star, four dining elves, three security rules, two pointers, and a nasty bug in a production tree!
 * <p>
 * Mr. Frost bursts into the office looking excited. "I've been analyzing our company's internal social network - you know, who knows who at North Pole Technologies. There's this famous theory called 'six degrees of separation' that says any two sentient beings are connected through at most six steps."
 * He pulls up a massive network diagram on his screen. "I want to test this theory on our network. Starting from one being, I need you to find who is the FURTHEST away in terms of connections - the individual at the end of the longest chain of 'friend-of-a-friend' relationships. If multiple beings are equally far, just give me the first one alphabetically."
 * <p>
 * <p>
 * Input format: starting being's name on the first line. All other lines contain bidirectional relationships in a format Name1,Name2.
 * Output: The name of the being who is furthest from the starting one (maximum degrees of separation). If multiple entities are at the same maximum distance, output the name of the one that comes first alphabetically.
 */
public class Day6 {

    @NotNull
    private final Map<String, List<String>> adjacencyList = new HashMap<>();

    public void addEdge(@NotNull String from, @NotNull String to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    @Nullable
    public String findFurthestConnection(@NotNull String rootNode) {
        Map<String, Integer> distancesFromRoot = calculateDistancesFromRoot(rootNode);
        int maxDistance = distancesFromRoot.values().stream().max(Integer::compare).orElse(0);
        return distancesFromRoot.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxDistance))
                .map(Map.Entry::getKey)
                .min(String::compareTo)
                .orElse(null);
    }

    private Map<String, Integer> calculateDistancesFromRoot(@NotNull String rootNode) {
        // Breadth-First Search (BFS)
        Map<String, Integer> distancesFromRoot = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        distancesFromRoot.put(rootNode, 0);
        visited.add(rootNode);
        queue.offer(rootNode);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            int currentDistanceFromRoot = distancesFromRoot.get(currentNode);
            List<String> currentConnections = adjacencyList.get(currentNode);
            if (currentConnections == null) continue;
            for (String connection : currentConnections) {
                if (!visited.contains(connection)) {
                    visited.add(connection);
                    int connectionDistance = currentDistanceFromRoot + 1;
                    distancesFromRoot.merge(connection, connectionDistance, Math::max);
                    queue.offer(connection);
                }
            }
        }

        return distancesFromRoot;
    }

}

class Runner {

    public static void main(String[] args) {
        List<String> inputLines = InputReader.readLines("inputs/day6_dataset.txt");
        String rootNode = inputLines.getFirst();
        Day6 day6 = new Day6();
        for (int i = 1; i < inputLines.size(); i++) {
            String[] parts = inputLines.get(i).split(",");
            String from = parts[0];
            String to = parts[1];
            day6.addEdge(from, to);
        }
        System.out.println(day6.findFurthestConnection(rootNode));
    }

}