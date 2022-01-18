/*
 * Copyright © 2012-2022 Gregory P. Moyer
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
package org.syphr.wordplay.core.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.wordplay.core.component.Board;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.Placement;
import org.syphr.wordplay.core.component.TileSet;
import org.syphr.wordplay.core.component.ValuedPlacement;
import org.syphr.wordplay.core.component.ValuedPlacementImpl;
import org.syphr.wordplay.core.config.Configuration;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

import com.google.common.collect.Collections2;

public abstract class AbstractRobot extends PlayerImpl implements Robot
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRobot.class);

    private RobotStrategy strategy;

    private Configuration configuration;

    private ExecutorService executor;

    public AbstractRobot()
    {
        super();
    }

    public AbstractRobot(UUID id)
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

    protected int getMaxThreads()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    protected ThreadFactory createThreadFactory()
    {
        return new DaemonThreadFactory();
    }

    protected ExecutorService getExecutor()
    {
        if (executor == null) {
            executor = Executors.newCachedThreadPool(createThreadFactory());
        }

        return executor;
    }

    @Override
    public Placement getPlacement(Board board)
    {
        findPlacements(board);
        return strategy.selectPlacement();
    }

    public void findPlacements(Board board)
    {
        Collection<ValuedPlacement> placements = strategy.getDataStructure();
        placements.clear();

        Collection<List<PieceWrapper>> candidates = getPlacementCandidates(getRack().getPieces());
        // if (LOGGER.isTraceEnabled())
        // {
        LOGGER.info("Found {} possible combinations and permutations", candidates.size());
        // }

        CompletionService<Collection<ValuedPlacement>> completionService = new ExecutorCompletionService<Collection<ValuedPlacement>>(getExecutor());

        Iterator<List<PieceWrapper>> candidateIter = candidates.iterator();
        int groupSize = (int) Math.ceil((double) candidates.size() / (double) getMaxThreads());
        for (int t = 0; t < getMaxThreads(); t++) {
            Collection<List<PieceWrapper>> candidateGroup = new ArrayList<List<PieceWrapper>>();

            for (int c = t * groupSize; c < (t + 1) * groupSize && candidateIter.hasNext(); c++) {
                candidateGroup.add(candidateIter.next());
            }

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Candidate group {} contains {} entries", t, candidateGroup.size());
            }

            completionService.submit(buildPlacementTask(candidateGroup, board));
        }
        assert (!candidateIter.hasNext());

        for (int i = 0; i < getMaxThreads(); i++) {
            try {
                placements.addAll(completionService.take().get());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e.getMessage(), e);
            } catch (ExecutionException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        if (LOGGER.isTraceEnabled()) {
            for (ValuedPlacement placement : placements) {
                LOGGER.trace("{}\t{}", placement.getPoints(), placement.getPieces());
            }
        }
    }

    private Callable<Collection<ValuedPlacement>> buildPlacementTask(final Collection<List<PieceWrapper>> candidates,
                                                                     final Board board)
    {
        return new Callable<Collection<ValuedPlacement>>()
        {
            @Override
            public Collection<ValuedPlacement> call()
            {
                Collection<ValuedPlacement> placements = new HashSet<ValuedPlacement>();

                // FIXME board is not thread safe
                Dimension boardDimension = board.getDimension();
                TileSet tiles = board.getTiles();

                for (int x = 0; x < boardDimension.width(); x++) {
                    for (int y = 0; y < boardDimension.height(); y++) {
                        for (int z = 0; z < boardDimension.depth(); z++) {
                            if (Thread.interrupted()) {
                                return placements;
                            }

                            Location location = Location.at(x, y, z);
                            if (tiles.getTile(location).hasPiece()) {
                                continue;
                            }

                            for (List<PieceWrapper> candidate : candidates) {
                                for (Orientation orientation : configuration.getOrientations()) {
                                    ValuedPlacementImpl placement = new ValuedPlacementImpl();
                                    placement.setPieces(unwrap(candidate));
                                    placement.setStartLocation(location);
                                    placement.setOrientation(orientation);

                                    /*
                                     * If this placement is the same as another accepted placement, there is no need
                                     * to check it. This can happen because the pieces have been wrapped to allow
                                     * piece groups that only differ by the wildcard letter selected to be added
                                     * together so they can each be tested. However, if the only difference between
                                     * two valid placements is the letter selected for a wildcard, there is no
                                     * scoring difference and so it can be skipped. For future strategy
                                     * consideration, the letter selected may impact future turns for both this
                                     * player and the opponent(s). Applying some sort of ranking mechanism to each
                                     * letter would allow a more strategic wildcard selection to override another.
                                     */
                                    if (placements.contains(placement)) {
                                        continue;
                                    }

                                    if (board.isValid(placement)) {
                                        placement.setPoints(board.calculatePoints(placement));
                                        placements.add(placement);
                                    }
                                }
                            }
                        }
                    }
                }

                return placements;
            }
        };
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

    /**
     * Pieces are normally implemented such that wildcards are considered equal.
     * This is because a wildcard has no letter by definition, even though one will
     * be selected when it is placed on the board for validating word choice.
     * However, when testing all possibilities for the robot, the wildcards are
     * expanded to all possible letter choices to test for valid words. Therefore,
     * in this use case two wildcards with the different letters must be treated as
     * unequal objects until the testing is complete.
     *
     * @author Gregory P. Moyer
     */
    protected static class PieceWrapper implements Piece
    {
        private final Piece delegate;

        public static PieceWrapper wrap(Piece piece)
        {
            return new PieceWrapper(piece);
        }

        public PieceWrapper(Piece piece)
        {
            delegate = piece;
        }

        @Override
        public void setLetter(Letter letter)
        {
            delegate.setLetter(letter);
        }

        @Override
        public Letter getLetter()
        {
            return delegate.getLetter();
        }

        @Override
        public int getValue()
        {
            return delegate.getValue();
        }

        @Override
        public boolean isWild()
        {
            return delegate.isWild();
        }

        @Override
        public PieceWrapper copy()
        {
            return new PieceWrapper(delegate.copy());
        }

        public Piece getPiece()
        {
            return delegate;
        }

        @Override
        public String toString()
        {
            return delegate.toString();
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((delegate.getLetter() == null) ? 0 : delegate.getLetter().hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            PieceWrapper other = (PieceWrapper) obj;
            if (delegate.getLetter() == null) {
                if (other.getPiece().getLetter() != null) {
                    return false;
                }
            } else if (!delegate.getLetter().equals(other.getPiece().getLetter())) {
                return false;
            }
            return true;
        }
    }

    /**
     * This class is almost identical to the default thread factory in
     * {@link Executors}, except that the threads are marked as daemon so that they
     * do not prevent JVM shutdown and the threads are named uniquely to this
     * utility class.
     */
    private static class DaemonThreadFactory implements ThreadFactory
    {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public DaemonThreadFactory()
        {
            group = Thread.currentThread().getThreadGroup();
            namePrefix = AbstractRobot.class.getSimpleName() + " Thread Pool " +
                         POOL_NUMBER.getAndIncrement() +
                         ", Thread ";
        }

        @Override
        public Thread newThread(Runnable r)
        {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (!t.isDaemon()) {
                t.setDaemon(true);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    }
}
