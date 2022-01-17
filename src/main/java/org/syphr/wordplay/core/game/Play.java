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
package org.syphr.wordplay.core.game;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.player.Player;

/**
 * A play is the result of a turn taken by a player that represents a record of
 * that turn. It includes important information about the turn, such as the
 * player and the placement made.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Play
{
    /**
     * Retrieve the player who made this play.
     * 
     * @return the associated player
     */
    public Player getPlayer();

    /**
     * Retrieve the placement that was made.
     * 
     * @return the placement
     */
    public Placement getPlacement();

    /**
     * Retrieve the points scored on this play.
     * 
     * @return the points
     */
    public int getPoints();

    // TODO
    // public Set<Word> getWords();
}
