package org.syphr.wordplay.bot;

import java.util.Collection;

import org.syphr.wordplay.core.ValuedPlacement;

/**
 * A robot strategy determines how a robot selects its next placement.
 * 
 * @author Gregory P. Moyer
 */
public interface RobotStrategy
{
    /**
     * Retrieve the data structure into which all possible placements will be put.
     * 
     * @return the placement data structure
     */
    public Collection<ValuedPlacement> getDataStructure();

    /**
     * Select a placement from the {@link #getDataStructure() data structure}. It is
     * guaranteed that this method will never be called before the data structure is
     * populated.
     * 
     * @return the selected placement from the data structure or <code>null</code>
     *         if no placement was selected
     */
    public ValuedPlacement selectPlacement();
}
