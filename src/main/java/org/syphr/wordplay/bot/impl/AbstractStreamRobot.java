package org.syphr.wordplay.bot.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.bot.Robot;
import org.syphr.wordplay.bot.RobotStrategy;
import org.syphr.wordplay.bot.impl.AbstractRobot.PieceWrapper;
import org.syphr.wordplay.core.board.Board;
import org.syphr.wordplay.core.board.Piece;
import org.syphr.wordplay.core.board.Placement;
import org.syphr.wordplay.core.board.TileSet;
import org.syphr.wordplay.core.board.ValuedPlacement;
import org.syphr.wordplay.core.board.ValuedPlacementImpl;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.impl.PlayerImpl;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

import com.google.common.collect.Collections2;

public class AbstractStreamRobot extends PlayerImpl implements Robot
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStreamRobot.class);

    private RobotStrategy strategy;

    private Configuration configuration;

    public AbstractStreamRobot()
    {
        super();
    }

    public AbstractStreamRobot(UUID id)
    {
        super(id);
    }

    @Override
    public void setStrategy(RobotStrategy strategy)
    {
        this.strategy = strategy;
    }

    @Override
    public RobotStrategy getStrategy()
    {
        return strategy;
    }

    @Override
    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration()
    {
        return configuration;
    }

    @Override
    public Placement getPlacement(Board board)
    {
        // FIXME board is not thread safe
        findPlacements(board, Collections.synchronizedCollection(strategy.getDataStructure()));
        return strategy.selectPlacement();
    }

    protected void findPlacements(Board board, Collection<ValuedPlacement> placements)
    {
        placements.clear();

        Collection<List<PieceWrapper>> candidates = getPlacementCandidates(getRack().getPieces());
        // if (LOGGER.isTraceEnabled())
        // {
        LOGGER.info("Found {} possible combinations and permutations", candidates.size());
        // }

        generateLocations(board).parallel()
                                .flatMap(location -> Collections.synchronizedCollection(candidates)
                                                                .stream()
                                                                .map(candidate -> new LocatedCandidate(location,
                                                                                                       candidate)))
                                .flatMap(candidate -> generatePlacements(board, candidate))
                                .filter(placement -> board.isValid(placement))
                                .peek(placement -> ((ValuedPlacementImpl) placement).setPoints(board.calculatePoints(placement)))
                                .collect(Collectors.toCollection(() -> placements));

        // candidates.parallelStream()
        // .flatMap(candidate -> generatePlacements(board, candidate))
        // .filter(placement -> board.isValid(placement))
        // .peek(placement ->
        // ((ValuedPlacementImpl)placement).setPoints(board.calculatePoints(placement)))
        // .collect(Collectors.toCollection(() -> placements));
    }

    private static class LocatedCandidate
    {
        public final Location location;
        public final List<PieceWrapper> candidate;

        public LocatedCandidate(Location location, List<PieceWrapper> candidate)
        {
            this.location = location;
            this.candidate = candidate;
        }
    }

    protected Stream<Location> generateLocations(Board board)
    {
        Builder<Location> builder = Stream.builder();

        Dimension boardDimension = board.getDimension();
        TileSet tiles = board.getTiles();

        for (int x = 0; x < boardDimension.getWidth(); x++) {
            for (int y = 0; y < boardDimension.getHeight(); y++) {
                for (int z = 0; z < boardDimension.getDepth(); z++) {
                    Location location = Location.at(x, y, z);
                    if (tiles.getTile(location).hasPiece()) {
                        continue;
                    }

                    builder.accept(location);
                }
            }
        }

        return builder.build();
    }

    protected Stream<Orientation> generateOrientations()
    {
        Builder<Orientation> builder = Stream.builder();

        for (Orientation orientation : configuration.getOrientations()) {
            builder.accept(orientation);
        }

        return builder.build();
    }

    protected Stream<ValuedPlacement> generatePlacements(Board board, LocatedCandidate lc)
    {
        Builder<ValuedPlacement> builder = Stream.builder();

        for (Orientation orientation : configuration.getOrientations()) {
            ValuedPlacementImpl placement = new ValuedPlacementImpl();
            placement.setPieces(unwrap(lc.candidate));
            placement.setStartLocation(lc.location);
            placement.setOrientation(orientation);

            builder.accept(placement);
        }

        return builder.build();
    }

    protected Stream<ValuedPlacement> generatePlacements(Board board, List<PieceWrapper> candidate)
    {
        return generateLocations(board).flatMap(location -> {
            Builder<ValuedPlacement> builder = Stream.builder();

            for (Orientation orientation : configuration.getOrientations()) {
                ValuedPlacementImpl placement = new ValuedPlacementImpl();
                placement.setPieces(unwrap(candidate));
                placement.setStartLocation(location);
                placement.setOrientation(orientation);

                builder.accept(placement);
            }

            return builder.build();
        });
    }

    protected Collection<List<PieceWrapper>> getPlacementCandidates(List<Piece> pieces)
    {
        Collection<List<PieceWrapper>> candidates = new HashSet<List<PieceWrapper>>();

        for (List<Piece> pieceGroup : getCombinationsAndPermutations(pieces)) {
            for (List<PieceWrapper> expandedPieceGroup : expandWildcards(Collections.<PieceWrapper>emptyList(),
                                                                         wrap(pieceGroup))) {
                candidates.add(expandedPieceGroup);
            }
        }

        return candidates;
    }

    protected Collection<List<PieceWrapper>> expandWildcards(List<PieceWrapper> prefix, List<PieceWrapper> list)
    {
        if (list.isEmpty()) {
            return Collections.singleton(prefix);
        }

        PieceWrapper nextPiece = list.get(0);
        List<PieceWrapper> newList = list.subList(1, list.size());

        if (!nextPiece.isWild()) {
            List<PieceWrapper> newPrefix = new ArrayList<PieceWrapper>(prefix);
            newPrefix.add(nextPiece);
            return expandWildcards(newPrefix, newList);
        }

        Collection<List<PieceWrapper>> expanded = new HashSet<List<PieceWrapper>>();
        for (Letter expandedLetter : configuration.getLetterFactory().getLetters()) {
            PieceWrapper newPiece = nextPiece.copy();
            newPiece.setLetter(expandedLetter);

            List<PieceWrapper> newPrefix = new ArrayList<PieceWrapper>(prefix);
            newPrefix.add(newPiece);
            expanded.addAll(expandWildcards(newPrefix, newList));
        }

        return expanded;
    }

    protected List<PieceWrapper> wrap(List<Piece> pieces)
    {
        List<PieceWrapper> wrapped = new ArrayList<PieceWrapper>();

        for (Piece piece : pieces) {
            wrapped.add(PieceWrapper.wrap(piece));
        }

        return wrapped;
    }

    protected List<Piece> unwrap(List<PieceWrapper> pieces)
    {
        List<Piece> unwrapped = new ArrayList<Piece>();

        for (PieceWrapper piece : pieces) {
            unwrapped.add(piece.getPiece());
        }

        return unwrapped;
    }

    protected <T> Collection<List<T>> getCombinationsAndPermutations(List<T> list)
    {
        Collection<List<T>> results = new HashSet<List<T>>(Collections2.permutations(list));

        int size = list.size();
        for (int i = 0; i < size; i++) {
            List<T> newList = new ArrayList<T>();
            newList.addAll(list.subList(0, i));
            newList.addAll(list.subList(i + 1, size));

            results.addAll(getCombinationsAndPermutations(newList));
        }

        return results;
    }
}
