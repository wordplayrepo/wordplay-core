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

import java.util.UUID;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.component.Rack;

/**
 * A player represents an entity that earns points by making plays in an attempt
 * to end the game with highest score.<br>
 * <br>
 * An instance of a player is expected to be linked to only one game. A player
 * can be tracked against multiple games by utilizing the {@link #getId() unique
 * identifier} to find all matching player instances.
 *
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Player
{
    /**
     * Set the unique identifier for this player.
     *
     * @param id the identifier
     */
    public void setId(UUID id);

    /**
     * Retrieve the unique identifier for this player.
     *
     * @return the identifier
     */
    public UUID getId();

    /**
     * Set the name of this player.
     *
     * @param name the name
     */
    public void setName(String name);

    /**
     * Retrieve the name of this player.
     *
     * @return the name
     */
    public String getName();

    /**
     * Set the rack associated with this player. The rack contains the player's
     * pieces that can be made into a placement on the player's turn.
     *
     * @param rack the rack
     */
    public void setRack(Rack rack);

    /**
     * Retrieve the rack associated with this player. The rack contains the player's
     * pieces that can be made into a placement on the player's turn.
     *
     * @return the rack
     */
    public Rack getRack();

    /**
     * Retrieve this player's current score.
     *
     * @return the score
     */
    public int getScore();

    /**
     * Add the given amount to this player's current score.
     *
     * @param value the value to add (may be negative)
     */
    public void addScore(int value);

    /**
     * Reset this player's score back to its value at the start of the game.
     */
    public void resetScore();

    /**
     * Resign this player from the game. If the player is already resigned, this
     * method has no effect.
     */
    public void resign();

    /**
     * Determine whether or not this player has resigned from the game.
     *
     * @return <code>true</code> if the player has resigned; <code>false</code>
     *         otherwise
     */
    public boolean isResigned();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
