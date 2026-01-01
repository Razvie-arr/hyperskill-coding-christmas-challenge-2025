package me.razviearr.advent;

import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static me.razviearr.advent.Day1.findTheMostCommonError;

/**
 * Welcome to your new job at North Pole Technologies, your best provider of every winter holiday! As you should already know, we at NPT host a once-in-a-year programming internship, which we've dubbed "Twelve Days of Coding"! You and your colleagues will tackle 12 challenging tasks, one each day, carefully crafted by our team lead, Mr. Frost! Only the best performing developers will be able to continue working on our fabulous projects, so don't get too cozy. Less talking, more grinding! Just follow the lyrics of our theme song!
 * <p>
 * On the first day of coding my team lead sent to me... One nasty bug in a production tree!
 * <p>
 * Mr. Frost drops a log file on your desk with a concerned look. "Our production monitoring system has been going haywire. We've got one error that's basically background noise at this point - happens constantly, we've learned to live with it. But look at this timeframe: 15:00 to 15:30. Something else went wrong during that timeframe, and it's getting buried under all the usual noise. I need you to dig through these logs and tell me what error actually spiked during the incident."
 * <p>
 * You have a log file with entries in the format HH:MM ErrorName . You need to find and submit the name of the most common error in the logs between 15:00 and 15:30, excluding the error that is most common throughout the entire day (that's the "background noise" one).
 */
public class Day1 {

    @NotNull
    public static String findTheMostCommonError(@NotNull List<String> input) {
        Map<String, Integer> countToError = new HashMap<>();
        for (String line : input) {
            String error = line.split(" ")[1];
            countToError.merge(error, 1, Integer::sum);
        }
        Optional<Map.Entry<String, Integer>> theMostCommonError = countToError.entrySet().stream().max(Map.Entry.comparingByValue());
        return theMostCommonError.map(Map.Entry::getKey).orElseThrow(() -> new IllegalStateException("The most common error not found!"));
    }

}

class Day1Runner {

    public static void main(String[] args) {
        List<String> input = InputReader.readLines("inputs/day1_dataset.txt");
        String backgroundNoiseError = findTheMostCommonError(input);
        LocalTime from = LocalTime.of(15, 0);
        LocalTime to = LocalTime.of(15, 30);
        List<String> incidentErrors = input.stream().filter(line -> {
            LocalTime time = LocalTime.parse(line.split(" ")[0]);
            String error = line.split(" ")[1];
            return !time.isBefore(from) && !time.isAfter(to) && !backgroundNoiseError.equals(error);
        }).toList();
        System.out.println(findTheMostCommonError(incidentErrors));
    }

}