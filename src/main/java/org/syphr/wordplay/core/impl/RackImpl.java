package org.syphr.wordplay.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.syphr.wordplay.core.Bag;
import org.syphr.wordplay.core.NoSuchPieceException;
import org.syphr.wordplay.core.NotEnoughPiecesException;
import org.syphr.wordplay.core.Rack;
import org.syphr.wordplay.core.board.Piece;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RackImpl implements Rack
{
    private final int maxPieces;

    private final List<Piece> pieces = new ArrayList<Piece>();

    @Override
    public void fill(Bag bag)
    {
        for (int i = pieces.size(); i < getMaxPieces() && !bag.isEmpty(); i++) {
            try {
                pieces.add(bag.getPiece());
            } catch (NotEnoughPiecesException e) {
                // guaranteed not to happen by checking for an empty bag above
            }
        }
    }

    @Override
    public void exchange(Bag bag)
    {
        int exchangeCount = Math.min(bag.getCount(), pieces.size());
        try {
            exchange(List.copyOf(pieces.subList(0, exchangeCount)), bag);
        } catch (NoSuchPieceException | NotEnoughPiecesException e) {
            // guaranteed not to happen by checking above
        }
    }

    @Override
    public void exchange(Collection<Piece> pieces, Bag bag) throws NoSuchPieceException, NotEnoughPiecesException
    {
        if (!this.pieces.containsAll(pieces)) {
            throw new NoSuchPieceException("Rack does not contain pieces selected for exchange");
        }

        resetWild();
        Collection<Piece> newPieces = bag.exchange(List.copyOf(pieces));

        this.pieces.removeAll(pieces);
        this.pieces.addAll(newPieces);
    }

    @Override
    public void clear(Bag bag)
    {
        resetWild();
        bag.returnPieces(getPieces());
        pieces.clear();
    }

    @Override
    public boolean isEmpty()
    {
        return pieces.isEmpty();
    }

    @Override
    public List<Piece> getPieces()
    {
        return List.copyOf(pieces);
    }

    @Override
    public void remove(Piece piece)
    {
        if (!pieces.remove(piece)) {
            throw new IllegalArgumentException("Piece does not exist in this rack");
        }
    }

    @Override
    public void remove(Collection<Piece> pieces)
    {
        for (Piece piece : pieces) {
            remove(piece);
        }
    }

    @Override
    public void add(Piece piece)
    {
        verifySpaceForAdd(1);
        pieces.add(piece);
        resetWild();
    }

    @Override
    public void add(Collection<Piece> pieces)
    {
        verifySpaceForAdd(pieces.size());
        this.pieces.addAll(pieces);
        resetWild();
    }

    protected void verifySpaceForAdd(int count)
    {
        if (pieces.size() + count > maxPieces) {
            throw new IllegalArgumentException("Cannot add " + count +
                                               " piece(s), the limit of " +
                                               maxPieces +
                                               " would be exceeded");
        }
    }

    @Override
    public int getMaxPieces()
    {
        return maxPieces;
    }

    // TODO this feels like hack or at least in the wrong place
    protected void resetWild()
    {
        for (Piece piece : pieces) {
            if (piece.isWild()) {
                piece.setLetter(null);
            }
        }
    }

}
