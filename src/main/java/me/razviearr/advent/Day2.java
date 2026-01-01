package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;

import java.util.Arrays;
import java.util.List;

/**
 * On the second day of coding my team lead sent to me... Two pointers
 * <p>
 * And a nasty bug in a production tree!
 * <p>
 * Mr. Frost appears at your desk holding two containers with "COCOA" written across them. "Wow it's cold outside today. Well... Like any day on the North Pole, really. We're preparing some hot beverages. The problem is, none of them matches the exact same sweetness level that our team likes. Help me out a bit. I'll email you a list of the sweetness level of each cocoa that we have with a target sweetness we want to achieve. Pick two cocoa varieties to mix together to get as close to the target as possible, okay?".
 * <p>
 * Input format:
 * Line 1: Target sweetness value (integer)
 * Line 2: Comma-separated integers representing sweetness levels of available cocoa varieties (sorted in ascending order)
 * Output: The average sweetness level of two cocoa varieties that is closest to the target sweetness. If the result is decimal, round up to the next integer.
 */
public class Day2 {

    public static double findCombination(int target, List<Integer> sweetnesses) {
        int left = 0;
        int right = sweetnesses.size() - 1;
        double bestAverage = (double) (sweetnesses.get(left) + sweetnesses.get(right)) / 2;
        while (left <= right) {
            double currentAverage = (double) (sweetnesses.get(left) + sweetnesses.get(right)) / 2.0;

            double bestDiff = Math.abs(target - bestAverage);
            double currentDiff = Math.abs(target - currentAverage);
            if (currentDiff < bestDiff) {
                bestAverage = currentAverage;
            }

            if (currentAverage < target) {
                left++;
            } else if (currentAverage > target) {
                right--;
            } else {
                break;
            }
        }
        return bestAverage;
    }

}

class Day2Runner {

    public static void main(String[] args) {
        List<String> input = InputReader.readLines("inputs/day2_dataset.txt");
        int targetSweetness = Integer.parseInt(input.getFirst());
        List<Integer> sweetnesses = Arrays.stream(input.get(1).split(",")).map(Integer::parseInt).toList();
        System.out.println(Day2.findCombination(targetSweetness, sweetnesses));
    }

}
