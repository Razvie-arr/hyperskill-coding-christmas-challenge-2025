package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * On the fifth day of coding my team lead sent to me... Five-pointed star,
 * <p>
 * Four dining elves, three security rules, two pointers, and a nasty bug in a production tree!
 * <p>
 * Mr. Frost walks in holding a sheet of graph paper covered in coordinate points. "We're decorating the office for the winter festival. I've been designing 5-pointed stars to hang from the ceiling, but I need to order the right amount of golden fabric to cover them. Each star is defined by 10 points - 5 outer points (the tips) and 5 inner points (where the edges meet). I need you to calculate the total area so I know how much material to buy."
 * He hands you a file with coordinates.
 * <p>
 * Input format: 10 lines, each containing a coordinate pair in format x,y (the points alternate between outer (tip) and inner points as you traverse the star's perimeter)
 * Output: The area of the star, rounded to 2 decimal places
 */
public class Day5 {

    @NotNull
    private final List<Coordinate> coordinates;

    public Day5(@NotNull List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public double calculateArea() {
        // Gauss's area formula used
        double blueLaces = calculateBlueLaces();
        double redLaces = calculateRedLaces();
        double absoluteDifference = Math.abs(blueLaces - redLaces);
        return absoluteDifference / 2;
    }

    private double calculateBlueLaces() {
        double blueLaces = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            double x = coordinates.get(i).x();
            double y = coordinates.get(i + 1).y();
            blueLaces += x * y;
        }
        double lastX = coordinates.getLast().x();
        double firstY = coordinates.getFirst().y();
        blueLaces += lastX * firstY;
        return blueLaces;
    }

    private double calculateRedLaces() {
        double redLaces = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            double y = coordinates.get(i).y();
            double x = coordinates.get(i + 1).x();
            redLaces += x * y;
        }
        double lastY = coordinates.getLast().y();
        double firstX = coordinates.getFirst().x();
        redLaces += firstX * lastY;
        return redLaces;
    }

    public record Coordinate(double x, double y) {
    }

}

class Day5Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day5_dataset.txt");
        List<Day5.Coordinate> coordinates = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            coordinates.add(new Day5.Coordinate(Double.parseDouble(parts[0]), Double.parseDouble(parts[1])));
        }
        Day5 day5 = new Day5(coordinates);
        double starArea = day5.calculateArea();
        System.out.printf("%.2f", starArea);
    }

}
