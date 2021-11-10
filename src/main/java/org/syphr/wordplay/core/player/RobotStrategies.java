package org.syphr.wordplay.core.player;

import java.util.List;
import java.util.Set;

import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.lang.Letter;

public class RobotStrategies
{
    public static RobotStrategy highestScore()
    {
        return new HighestScoreStrategy();
    }

    public static RobotStrategy randomSelection()
    {
        return new RandomSelectionStrategy();
    }

    public static RobotStrategy saveEnablingLetters(double maxPointSacrificePercent,
                                                    int minPointThreshold,
                                                    Set<Letter> enablingLetters)
    {
        return new SaveEnablingLettersStrategy(maxPointSacrificePercent, minPointThreshold, enablingLetters);
    }

    public static RobotStrategy saveEnablingLetters(int letterCount,
                                                    double maxPointSacrificePercent,
                                                    int minPointThreshold,
                                                    Configuration configuration)
    {
        return new SaveEnablingLettersStrategy(letterCount, maxPointSacrificePercent, minPointThreshold, configuration);
    }

    public static List<RobotStrategy> getAllStrategies(Configuration configuration)
    {
        return List.of(highestScore(), randomSelection(), saveEnablingLetters(3, 0.3, 40, configuration));
    }

    private RobotStrategies()
    {
        /*
         * Factory pattern
         */
    }
}
