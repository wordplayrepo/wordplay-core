package org.syphr.wordplay.core;

import javax.annotation.concurrent.NotThreadSafe;

import org.syphr.wordplay.core.config.Configuration;

/**
 * A rack factory generates racks based on the given configuration. A rack
 * represents a player's current set of playable pieces.
 * 
 * @author Gregory P. Moyer
 */
@NotThreadSafe
public interface RackFactory
{
    /**
     * Create a new rack based on the given configuration.
     * 
     * @param configuration
     *            the configuration
     * @return a new rack
     */
    public Rack create(Configuration configuration);
}
