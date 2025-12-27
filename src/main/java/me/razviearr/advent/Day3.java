package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * On the third day of coding my team lead sent to me... Three security rules,
 * <p>
 * Two pointers, and a nasty bug in a production tree!
 * <p>
 * Mr. Frost is quite sad today. The reindeer system got hacked. Apparently, Rudolph had rednose set as his password. Was not hard to break at all! The cybersecurity team developed a new system where each reindeer has to come up with 50 different passwords, out of which the tech team will choose the best one on a set of three rules.
 * <p>
 * Oh, and by "the tech team" they mean you. Good luck!
 * <p>
 * Input: list of 50 passwords chosen by reindeer, each on a new line
 * Output: A password with the highest security score based on three rules. In case of ties, the earlier password in the file takes precedence.
 * <p>
 * The three rules:
 * <p>
 * 1. Base password security score is its length.
 * <p>
 * 2. Every password should have at least: one lowercase letter, one uppercase letter, one digit, one special symbol (!@#$%^&*). If any of these categories are missing, multiply base score by 0.75 for each missing category.
 * <p>
 * 3. Every password should minimize repeated characters. If at least 30% of password consists of the same character, subtract the number of occurrences of that character from the score (applied only to the most frequent character).
 * <p>
 * Rules are applied sequentially.
 */

public class Day3 {

    public static void main(String[] args) {
        List<String> input = InputReader.readLines("inputs/day3_dataset.txt");
        Day3 day3 = new Day3(input);
        System.out.println(day3.calculateBestPassword());
    }

    @NotNull
    private final List<String> passwords;
    private static final Set<Character> SPECIAL_SYMBOLS = Set.of('!', '@', '#', '$', '%', '^', '&', '*');

    public Day3(@NotNull List<String> passwords) {
        this.passwords = passwords;
    }

    @NotNull
    public String calculateBestPassword() {
        double bestScore = 0;
        String bestPassword = "";
        for (String password : passwords) {
            double score = calculateScore(password);
            if (score > bestScore) {
                bestScore = score;
                bestPassword = password;
            }
        }
        return bestPassword;
    }

    private double calculateScore(@NotNull String password) {
        double score = password.length();
        int missingRequirements = countMissingRequirements(password);
        for (int i = 0; i < missingRequirements; i++) {
            score = score * 0.75;
        }
        score -= calculateRepetitionPenalty(password);
        return score;
    }

    private int countMissingRequirements(@NotNull String password) {
        boolean hasUppercaseLetter = false;
        boolean hasLowercaseLetter = false;
        boolean hasSpecialSymbol = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!hasUppercaseLetter && Character.isUpperCase(c)) {
                hasUppercaseLetter = true;
            } else if (!hasLowercaseLetter && Character.isLowerCase(c)) {
                hasLowercaseLetter = true;
            } else if (!hasDigit && Character.isDigit(c)) {
                hasDigit = true;
            } else if (!hasSpecialSymbol && SPECIAL_SYMBOLS.contains(c)) {
                hasSpecialSymbol = true;
            }
            if (hasUppercaseLetter && hasLowercaseLetter && hasDigit && hasSpecialSymbol) {
                return 0;
            }
        }
        int missing = 0;
        if (!hasUppercaseLetter) {
            missing++;
        }
        if (!hasLowercaseLetter) {
            missing++;
        }
        if (!hasDigit) {
            missing++;
        }
        if (!hasSpecialSymbol) {
            missing++;
        }
        return missing;
    }

    private int calculateRepetitionPenalty(@NotNull String password) {
        Map<Character, Integer> charToCount = new HashMap<>();
        int maxCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            int currentCount = charToCount.merge(c, 1, Integer::sum);

            if (currentCount > maxCount) {
                maxCount = currentCount;
            }
        }
        if (maxCount >= password.length() * 0.3) {
            return maxCount;
        }

        return 0;
    }

}
