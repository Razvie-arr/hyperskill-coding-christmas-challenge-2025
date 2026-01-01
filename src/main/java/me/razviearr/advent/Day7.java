package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * On the seventh day of coding my team lead sent to me... Seven Bridges of Königsberg,
 * <p>
 * Six handshakes, five-pointed star, four dining elves, three security rules, two pointers, and a nasty bug in a production tree!
 * <p>
 * Mr. Frost walks in carrying a historical map. "Have you heard of the Seven Bridges of Königsberg? It's one of the most famous problems in mathematics. The city had seven bridges, and people wondered if you could walk a path crossing each bridge exactly once."
 * <p>
 * He pulls up a modern map on his screen showing a complex network of land masses connected by 700 bridges. "We have a similar problem here at the North Pole - our transportation network has grown massive. I need to know: can our delivery routes cross each bridge exactly once? And if not, what's the MINIMUM number of times we have to go through a bridge we have already visited to cover all bridges in our network?"
 * <p>
 * Input format: List of landmasses connected by bridges, one per line in format LandMass1,LandMass2 (remember that bridges are bidirectional)
 * Output: The minimum number of additional bridge crossings required (beyond crossing each bridge once). Or 0 if it's possible to cross each bridge exactly once.
 */
public class Day7 {

    @NotNull
    private final Map<String, List<String>> landMassToConnections = new HashMap<>();

    public void addBridge(@NotNull String from, @NotNull String to) {
        // bidirectional
        landMassToConnections.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        landMassToConnections.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }

    public long computeAdditionalBridgeCrossings() {
        long oddDegreeNodesCount = landMassToConnections.values().stream()
                .filter(connections -> connections.size() % 2 == 1)
                .count();
        if (oddDegreeNodesCount == 0 || oddDegreeNodesCount == 2) {
            return 0; // if 0 it's Eulerian cycle, if 2 it's Eulerian path
        }
        return (oddDegreeNodesCount / 2) - 1;
    }

}

class Day7Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day7_dataset.txt");
        Day7 day7 = new Day7();
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",");
            day7.addBridge(parts[0], parts[1]);
        }
        System.out.println(day7.computeAdditionalBridgeCrossings());
    }

}
