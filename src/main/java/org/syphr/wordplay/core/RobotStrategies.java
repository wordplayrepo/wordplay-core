package org.syphr.wordplay.core;

import java.util.ArrayList;
import java.util.List;

import org.syphr.wordplay.core.impl.HighestScoreStrategy;
import org.syphr.wordplay.core.impl.RandomSelectionStrategy;
import org.syphr.wordplay.core.impl.SaveEnablingLettersStrategy;

// TODO huge problem here - strategies are not immutable
public class RobotStrategies
{
    private static final RobotStrategy HIGHEST_SCORE = new HighestScoreStrategy();

    private static final RobotStrategy RANDOM_SELECTION = new RandomSelectionStrategy();

    private static final RobotStrategy SAVE_ENABLING_LETTERS = new SaveEnablingLettersStrategy();

    private static final List<RobotStrategy> ALL_STRATEGIES = new ArrayList<RobotStrategy>();
    static
    {
        ALL_STRATEGIES.add(HIGHEST_SCORE);
        ALL_STRATEGIES.add(RANDOM_SELECTION);
        ALL_STRATEGIES.add(SAVE_ENABLING_LETTERS);
    }

    public static RobotStrategy highestScore()
    {
        return HIGHEST_SCORE;
    }

    public static RobotStrategy randomSelection()
    {
        return RANDOM_SELECTION;
    }

    public static RobotStrategy saveEnablingLetters()
    {
        return SAVE_ENABLING_LETTERS;
    }

    public static List<RobotStrategy> getAllStrategies()
    {
        return new ArrayList<RobotStrategy>(ALL_STRATEGIES);
    }

    private RobotStrategies()
    {
        /*
         * Factory pattern
         */
    }
}
