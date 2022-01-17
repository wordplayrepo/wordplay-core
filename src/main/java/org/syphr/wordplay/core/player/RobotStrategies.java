/*
 * Copyright © 2012-2022 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
