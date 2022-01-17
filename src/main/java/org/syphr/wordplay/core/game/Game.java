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

import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.component.Bag;
import org.syphr.wordplay.core.component.Board;
import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.component.PlacementException;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.player.Player;

/**
 * A game represents a grouping of game elements (i.e. the players, the board,
 * the bag of pieces).
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface Game
{
    /**
     * Retrieve the unique identifier for this game.
     * 
     * @return the identifier
     */
    public UUID getId();

    /**
     * The configuration for this game. The configuration contains parameters that
     * define the game limits, such as the total number of pieces and the board
     * dimensions.
     * 
     * @return the configuration
     */
    public Configuration getConfiguration();

    /**
     * Retrieve the board associated with this game. The board provides the playing
     * surface on which pieces are placed.
     * 
     * @return the board
     */
    public Board getBoard();

    /**
     * Retrieve the bag of pieces for this game. The bag contains all of the pieces
     * that are not on the board or in a player's rack.
     * 
     * @return the bag of pieces
     */
    public Bag getBag();

    /**
     * Retrieve the set of players in this game.
     * 
     * @return the players
     */
    public Set<Player> getPlayers();

    /**
     * Retrieve the set of players in this game sorted by score in descending order.
     * 
     * @return the players by descending rank
     */
    public SortedSet<Player> getRankedPlayers();

    /**
     * Add a player to this game.
     * 
     * @param player the player to add
     */
    public void addPlayer(Player player);

    /**
     * Remove a player from this game.
     * 
     * @param player the player to remove
     */
    public void removePlayer(Player player);

    /**
     * Set the given player as current (it is now the given player's turn).
     * 
     * @param player the player to mark as current
     * 
     * @throws IllegalArgumentException if the player is not a member of this game
     */
    public void setCurrentPlayer(Player player) throws IllegalArgumentException;

    /**
     * Retrieve the current player (the player whose turn it is now).
     * 
     * @return the current player or <code>null</code> if the game has not started
     */
    public Player getCurrentPlayer();

    /**
     * Play the {@link #getCurrentPlayer() current player's} turn by making the
     * given placement on the board.
     * 
     * @param placement the placement to make
     * 
     * @throws PlacementException if the placement is not valid
     */
    public void play(Placement placement) throws PlacementException;

    /**
     * Complete the current player's turn and move play to the next player.
     */
    public void nextTurn();

    /**
     * Retrieve the last play, which includes the placement made and the player who
     * made it.
     * 
     * @return the last play
     */
    public Play getLastPlay();

    /**
     * Determine whether or not this game has been started.
     * 
     * @return <code>true</code> if the game has been started; <code>false</code>
     *         otherwise (note that the fact that this method returns
     *         <code>true</code> does not preclude {@link #isEnded()} from also
     *         returning <code>true</code> since this method simply determines
     *         whether or not this game was ever started)
     */
    public boolean isStarted();

    /**
     * Determine whether or not this game has ended.
     * 
     * @return <code>true</code> if this game has ended; <code>false</code>
     *         otherwise
     */
    public boolean isEnded();
}
