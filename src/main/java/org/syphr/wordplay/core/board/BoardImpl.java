package org.syphr.wordplay.core.board;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.lang.Dictionary;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardImpl implements Board
{
    private final Dimension dimension;
    private final Set<Orientation> orientations;
    private final Location start;
    private final TileSetFactory tileSetFactory;
    private final WordFactory wordFactory;
    private final Dictionary dictionary;
    private final ScoreCalculator scoreCalc;

    private TileSet tileset;

    public BoardImpl(Dimension dimension,
                     Set<Orientation> orientations,
                     Location start,
                     TileSetFactory tileSetFactory,
                     WordFactory wordFactory,
                     Dictionary dictionary,
                     ScoreCalculator scoreCalc)
    {
        this.dimension = dimension;
        this.orientations = Set.copyOf(orientations);
        this.start = start;
        this.tileSetFactory = tileSetFactory;
        this.wordFactory = wordFactory;
        this.dictionary = dictionary;
        this.scoreCalc = scoreCalc;
    }

    @Override
    public Dimension getDimension()
    {
        return dimension;
    }

    @Override
    public Location getStart()
    {
        return start;
    }

    @Override
    public Set<Orientation> getOrientations()
    {
        return Set.copyOf(orientations);
    }

    @Override
    public TileSet getTiles()
    {
        if (tileset == null) {
            synchronized (this) {
                if (tileset == null) {
                    tileset = tileSetFactory.createTileSet();
                }
            }
        }

        return tileset;
    }

    @Override
    public boolean isValid(Placement placement)
    {
        log.trace("Checking placement validity: {}", placement);

        SortedMap<Location, Piece> pieces = getPieces(placement);
        return isLocationSetValid(pieces.keySet())
               && isWordSetValid(wordFactory.getWords(pieces, placement.getOrientation(), this));
    }

    @Override
    public int place(Placement placement) throws InvalidLocationException, InvalidWordException
    {
        log.trace("Attempting to make placement {}", placement);

        SortedMap<Location, Piece> pieces = getPieces(placement);

        if (!isLocationSetValid(pieces.keySet())) {
            // TODO - need more info
            throw new InvalidLocationException("invalid location");
        }

        Set<Word> words = wordFactory.getWords(pieces, placement.getOrientation(), this);
        if (!isWordSetValid(words)) {
            // TODO - need more info
            throw new InvalidWordException("invalid word");
        }

        Map<Location, List<TileAttribute>> attributes = getTiles().getAttributes(pieces.keySet());
        int points = scoreCalc.getScore(pieces, words, attributes);

        updateTiles(pieces);

        return points;
    }

    @Override
    public int calculatePoints(Placement placement)
    {
        SortedMap<Location, Piece> pieces = getPieces(placement);
        Set<Word> words = wordFactory.getWords(pieces, placement.getOrientation(), this);
        Map<Location, List<TileAttribute>> attributes = getTiles().getAttributes(pieces.keySet());

        return scoreCalc.getScore(pieces, words, attributes);
    }

    protected SortedMap<Location, Piece> getPieces(Placement placement)
    {
        log.trace("Building piece map from placement: {}", placement);

        SortedMap<Location, Piece> map = new TreeMap<Location, Piece>();

        Orientation orientation = placement.getOrientation();
        log.trace("Orientation: {}", orientation);

        Location location = placement.getStartLocation();
        log.trace("Start location: {}", location);

        for (Iterator<Piece> iter = placement.getPieces().iterator(); iter.hasNext();) {
            Piece piece = iter.next();

            log.trace("Found piece ({}) at {}", piece, location);
            map.put(location, piece);

            if (!iter.hasNext()) {
                break;
            }

            do {
                location = orientation.move(location, 1);
                log.trace("Testing next location at {}", location);
            } while (getTiles().getTile(location).hasPiece());
        }

        return map;
    }

    /*
     * @formatter:off
     *
     * Four rules are tested for validity:
     *  1) Each location in the placement must be on the board.
     *  2) Each location in the placement must be on an empty tile.
     *  3) If the start location is empty, at least one location in the placement
     *     must be the start location.
     *  4) If the start location is not empty, at least one location in the 
     *     placement must have at least one piece next to it already on the board.
     *
     * @formatter:on
     */
    protected boolean isLocationSetValid(Set<Location> locations)
    {
        log.trace("Testing location validity: {}", locations);

        for (Location location : locations) {
            // TODO by eliminating overlapping pieces, a possible game mechanic is removed -
            // make this config
            log.trace("Testing location at {} for board dimensions and overlapping pieces", location);
            if (!dimension.contains(location) || getTiles().getTile(location).hasPiece()) {
                log.debug("Location at {} is invalid", location);
                return false;
            }
        }

        // TODO requiring a start location should be a configurable item - perhaps use a
        // null board start to indicate
        Location boardStart = getStart();
        if (!getTiles().getTile(boardStart).hasPiece()) {
            log.trace("Board has no pieces yet, testing placement against start location {}", boardStart);
            return locations.contains(boardStart);
        }

        for (Location location : locations) {
            log.trace("Testing location at {} for an adjacent piece", location);
            if (hasAdjacentPiece(location)) {
                return true;
            }
        }

        log.debug("Placement location is invalid, no adjacent pieces");
        return false;
    }

    protected boolean isWordSetValid(Set<Word> words)
    {
        log.trace("Testing word set for validity: {}", words);

        if (words.isEmpty()) {
            log.debug("Word set is invalid, set is empty");
            return false;
        }

        for (Word word : words) {
            log.trace("Testing word \"{}\" against the dictionary", word.getText());
            if (!dictionary.isValid(word.getText())) {
                log.debug("Word \"{}\" is invalid, not in the dictionary", word.getText());
                return false;
            }
        }

        return true;
    }

    protected boolean hasAdjacentPiece(Location location)
    {
        for (Orientation orientation : orientations) {
            if (hasAdjacentPiece(location, orientation)) {
                log.trace("Found piece adjacent to {} along {}", location, orientation);
                return true;
            }
        }

        log.trace("No pieces found adjacent to {}", location);
        return false;
    }

    protected boolean hasAdjacentPiece(Location location, Orientation orientation)
    {
        return getTiles().getTile(orientation.move(location, 1)).hasPiece()
               || getTiles().getTile(orientation.move(location, -1)).hasPiece();
    }

    protected void updateTiles(SortedMap<Location, Piece> pieces)
    {
        for (Entry<Location, Piece> entry : pieces.entrySet()) {
            Location location = entry.getKey();
            Piece piece = entry.getValue();

            log.trace("Updating tile set with new piece {} at {}", piece, location);
            getTiles().getTile(location).setPiece(piece);
        }
    }
}
