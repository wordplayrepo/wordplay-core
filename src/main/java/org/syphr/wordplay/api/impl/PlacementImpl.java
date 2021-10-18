package org.syphr.wordplay.api.impl;

import java.util.List;

import org.syphr.wordplay.api.Location;
import org.syphr.wordplay.api.Orientation;
import org.syphr.wordplay.api.Piece;
import org.syphr.wordplay.api.Placement;

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
