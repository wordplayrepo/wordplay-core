package org.syphr.wordplay.core.game;

import java.util.UUID;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.component.Rack;
import org.syphr.wordplay.core.xml.SchemaVersion;
import org.syphr.wordplay.core.xml.UnsupportedSchemaVersionException;

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

    /**
     * Serialize the state of this player to XML. This functionality is intended for
     * use when serializing the state of the game.
     *
     * @param version the schema version to which the serialized output must
     *                validate
     * 
     * @return the serialized XML version of this player
     * 
     * @throws UnsupportedSchemaVersionException if this player instance does not
     *                                           support serializing to the
     *                                           requested schema version
     */
    public String serialize(SchemaVersion version) throws UnsupportedSchemaVersionException;

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
