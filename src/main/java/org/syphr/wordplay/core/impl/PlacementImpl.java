package org.syphr.wordplay.core.impl;

import java.util.List;

import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.Placement;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacementImpl implements Placement
{
    private Location startLocation;
    private Orientation orientation;
    private List<Piece> pieces;
}
