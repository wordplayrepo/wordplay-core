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

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.component.Board;
import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.config.Configuration;

/**
 * A robot is an artificial player that follows a defined strategy to create
 * placements.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Robot extends Player
{
    /**
     * Set the strategy by which this robot will play.
     * 
     * @param strategy
     *            the strategy
     */
    public void setStrategy(RobotStrategy strategy);

    /**
     * Retrieve the strategy by which this robot is playing.
     * 
     * @return the strategy
     */
    public RobotStrategy getStrategy();

    /**
     * Set the configuration. The configuration contains parameters that define
     * the game.
     * 
     * @param configuration
     *            the configuration
     */
    public void setConfiguration(Configuration configuration);

    /**
     * Retrieve the configuration. The configuration contains parameters that
     * define the game.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration();

    /**
     * Determine this robot's next placement based on the current state of the
     * given board and the robot's {@link #getStrategy() strategy}.
     * 
     * @param board
     *            the game board
     * @return the robot's requested placement
     */
    public Placement getPlacement(Board board);
}
