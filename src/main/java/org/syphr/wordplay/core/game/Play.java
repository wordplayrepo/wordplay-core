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
