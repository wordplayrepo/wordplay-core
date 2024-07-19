/*
 * Copyright © 2012-2024 Gregory P. Moyer
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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.component.Bag;
import org.syphr.wordplay.core.component.Board;
import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.component.PlacementException;
import org.syphr.wordplay.core.component.Rack;
import org.syphr.wordplay.core.component.RackFactory;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.event.EventBus;
import org.syphr.wordplay.core.player.Player;

import com.google.common.collect.Iterators;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameImpl implements Game
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GameImpl.class);

    private static final Comparator<Player> RANKING_COMPARATOR = new Comparator<>()
    {
        @Override
        public int compare(Player p1, Player p2)
        {
            return -1 * (p1.getScore() - p2.getScore());
        }
    };

    private final Set<Player> players = new LinkedHashSet<>();

    private final Board board;
    private final Bag bag;
    private final RackFactory rackFactory;
    private final Configuration configuration;

    private UUID id = UUID.randomUUID();

    private Play lastPlay;

    private Iterator<Player> turns;
    private Player currentPlayer;

    @Override
    public UUID getId()
    {
        return id;
    }

    protected void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public Configuration getConfiguration()
    {
        return configuration;
    }

    @Override
    public Bag getBag()
    {
        return bag;
    }

    @Override
    public Board getBoard()
    {
        return board;
    }

    @Override
    public Set<Player> getPlayers()
    {
        return Collections.unmodifiableSet(players);
    }

    @Override
    public SortedSet<Player> getRankedPlayers()
    {
        SortedSet<Player> ranked = new TreeSet<>(RANKING_COMPARATOR);
        ranked.addAll(players);

        return ranked;
    }

    @Override
    public void addPlayer(Player player)
    {
        if (isStarted()) {
            throw new IllegalStateException("Cannot add a player after the game has started");
        }

        LOGGER.trace("Adding player {}", player);

        Rack rack = rackFactory.createRack();
        rack.fill(getBag());
        player.setRack(rack);

        players.add(player);
    }

    @Override
    public void removePlayer(Player player)
    {
        if (isStarted()) {
            throw new IllegalStateException("Cannot remove a player after the game has started (resign instead)");
        }

        LOGGER.trace("Removing player {}", player);

        players.remove(player);
    }

    @Override
    public boolean isStarted()
    {
        return currentPlayer != null;
    }

    @Override
    public void setCurrentPlayer(Player player) throws IllegalArgumentException
    {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("Player " + player + " is not a member of this game");
        }

        currentPlayer = player;
    }

    @Override
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    @Override
    public void nextTurn()
    {
        if (turns == null) {
            turns = Iterators.cycle(players);

            if (currentPlayer != null) {
                int playerCount = players.size();
                int counter = 0;
                for (; counter < playerCount; counter++) {
                    if (turns.next().equals(currentPlayer)) {
                        break;
                    }
                }

                // TODO re-evaluate this based on the fact that current player cannot be set
                // unless it is a member of the game
                if (counter == playerCount) {
                    throw new IllegalStateException("The current player (" + currentPlayer.getId() +
                                                    ") is not in the list of players");
                }

                fireTurnStarted();
                return;
            }
        }

        // TODO handle resignations
        setCurrentPlayer(turns.next());
        fireTurnStarted();
    }

    @Override
    public void play(final Placement placement) throws PlacementException
    {
        final int points = getBoard().place(placement);
        final Player player = getCurrentPlayer();
        lastPlay = new Play()
        {
            @Override
            public Player getPlayer()
            {
                return player;
            }

            @Override
            public Placement getPlacement()
            {
                return placement;
            }

            @Override
            public int getPoints()
            {
                return points;
            }
        };

        fireTurnEnded();
    }

    @Override
    public Play getLastPlay()
    {
        return lastPlay;
    }

    @Override
    public boolean isEnded()
    {
        if (!isStarted()) {
            return false;
        }

        for (Player player : getPlayers()) {
            if (player.getRack().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    protected void fireTurnStarted()
    {
        EventBus.post(new TurnStartEvent(this));
    }

    protected void fireTurnEnded()
    {
        EventBus.post(new TurnEndEvent(this));
    }

    protected void fireGameEnded()
    {
        EventBus.post(new GameEndEvent(this));
    }
}
