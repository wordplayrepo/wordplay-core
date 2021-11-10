package org.syphr.wordplay.core.component;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Generator of {@link TileSet tile sets}.
 * 
 * @author Gregory P. Moyer
 */
@ThreadSafe
public interface TileSetFactory
{
	/**
	 * Create a new tile set.
	 * 
	 * @return the new tile set
	 */
	public TileSet createTileSet();
}
