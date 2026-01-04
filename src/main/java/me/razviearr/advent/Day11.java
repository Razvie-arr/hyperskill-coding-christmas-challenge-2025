package me.razviearr.advent;

import lombok.Getter;
import lombok.Setter;
import me.razviearr.advent.utils.InputReader;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * You find Mr. Frost in the office's bakery. "Look what's happening here. Our eleven gingerbread workers need to bake various cookies. But it's not that straightforward. You can't frost cookies before you've baked them, and you can't make sandwich cookies before both halves are ready. So some actions must be done before the others are performed. Surely you understand how complex baking can really get."
 * <p>
 * He hands you a production schedule. "Here. Each baking action has a duration and a list of which actions must be completed first. Up to eleven gingerbread people can work in parallel on independent baking actions, but an action can only start once ALL its dependencies are done. We need this done by lunch. Can you tell me how much time will it approximately take? We need to hurry up, so don't waste your time calculating it down to a second, an answer using critical path schedule will be good enough. They teach kids outside the North Pole about that, right?"
 * <p>
 * <b>Input format: One action per line: action_id,duration,dependencies</b>
 * <ul>
 *     <li> action_id: Unique identifier (0, 1, 2, ...)</li>
 *     <li> duration: Minutes to complete this action</li>
 *     <li> dependencies: Colon-separated list of actions IDs that must finish first, or none if no dependencies</li>
 * </ul>
 * <p>
 * <b>Example</b>: 5,20,2:3 means action 5 takes 20 minutes and requires actions 2 and 3 to be done first.
 * <p>
 * <b>Output</b>: Minimum time in minutes to complete all actions with 11 workers working in parallel.
 */
class Day11 {

    @NotNull
    private final List<Action> actions;

    public Day11(@NotNull List<Action> actions) {
        this.actions = actions;
    }

    public int calculateMinTimeToCompleteProject() {
        calculateTotalScores();
        setInitialRemainingDependencies();
        PriorityQueue<Action> readyQueue = createReadyActionsQueue();
        PriorityQueue<ActionEvent> activeQueue = createActiveActionsQueue();
        fillWithActionsWithNoDependencies(readyQueue);

        int currentTime = 0;
        int workersAvailable = 11;

        while (!readyQueue.isEmpty() || !activeQueue.isEmpty()) {
            while (workersAvailable > 0 && !readyQueue.isEmpty()) {
                Action action = readyQueue.poll();
                int finishTime = currentTime + action.getDuration();
                activeQueue.add(new ActionEvent(action, finishTime));
                workersAvailable--;
            }

            if (!activeQueue.isEmpty()) {
                ActionEvent completed = activeQueue.poll();
                currentTime = completed.finishTime();
                workersAvailable++;

                unlockSuccessors(completed.action(), readyQueue);

                while (!activeQueue.isEmpty() && activeQueue.peek().finishTime() == currentTime) {
                    // parallel work
                    ActionEvent alsoCompleted = activeQueue.poll();
                    workersAvailable++;
                    unlockSuccessors(alsoCompleted.action(), readyQueue);
                }
            }
        }

        return currentTime;
    }

    private void calculateTotalScores() {
        for (int i = actions.size() - 1; i >= 0; i--) {
            calculateTotalScore(i);
        }
    }

    private void setInitialRemainingDependencies() {
        for (Action action : actions) {
            action.setRemainingDependencies(action.getDependencies().size());
        }
    }

    private int calculateTotalScore(int actionId) {
        Action currentAction = actions.get(actionId);
        if (currentAction.getTotalScore() > 0) {
            return currentAction.getTotalScore();
        }
        int maxSuccessorScore = 0;
        for (int successorId : currentAction.getSuccessors()) {
            maxSuccessorScore = Math.max(maxSuccessorScore, calculateTotalScore(successorId));
        }
        int totalScore = currentAction.getDuration() + maxSuccessorScore;
        currentAction.setTotalScore(totalScore);
        return totalScore;
    }

    @NotNull
    private PriorityQueue<Action> createReadyActionsQueue() {
        return new PriorityQueue<>((a, b) -> {
            int scoreCompare = Integer.compare(b.getTotalScore(), a.getTotalScore());
            if (scoreCompare != 0) return scoreCompare;
            return Integer.compare(b.getDuration(), a.getDuration());
        });
    }

    @NotNull
    private PriorityQueue<ActionEvent> createActiveActionsQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(ActionEvent::finishTime));
    }


    private void fillWithActionsWithNoDependencies(@NotNull PriorityQueue<Action> readyTasksQueue) {
        for (Action action : actions) {
            if (action.getRemainingDependencies() == 0) {
                readyTasksQueue.add(action);
            }
        }
    }

    private void unlockSuccessors(@NotNull Action action, @NotNull PriorityQueue<Action> readyQueue) {
        for (int successorId : action.getSuccessors()) {
            Action successor = actions.get(successorId);
            successor.decrementDependencies();
            if (successor.getRemainingDependencies() == 0) {
                readyQueue.add(successor);
            }
        }
    }

}

class Day11Runner {

    public static void main(String[] args) {
        List<String> lines = InputReader.readLines("inputs/day11_dataset.txt");
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < lines.size(); ++i) {
            String[] parts = lines.get(i).split(",");
            int duration = Integer.parseInt(parts[1]);
            String dependencies = parts[2];
            Action action = new Action(duration);
            if (!dependencies.equals("none")) {
                String[] dependenciesArray = dependencies.split(":");
                for (String dependency : dependenciesArray) {
                    int dependencyId = Integer.parseInt(dependency);
                    action.addDependency(dependencyId);
                    actions.get(dependencyId).addSuccessor(i);
                }
            }
            actions.add(action);
        }

        Day11 day11 = new Day11(actions);
        System.out.println(day11.calculateMinTimeToCompleteProject());
    }

}

@Getter
class Action {

    private final int duration;
    @NotNull
    private final List<Integer> dependencies = new ArrayList<>();
    @NotNull
    private final List<Integer> successors = new ArrayList<>();
    @Setter
    private int remainingDependencies;
    @Setter
    private int totalScore = 0;

    public Action(int duration) {
        this.duration = duration;
    }

    public void addSuccessor(int successor) {
        successors.add(successor);
    }

    public void addDependency(int dependency) {
        dependencies.add(dependency);
    }

    public void decrementDependencies() {
        this.remainingDependencies--;
    }

}

record ActionEvent(@NotNull Action action, int finishTime) {

}