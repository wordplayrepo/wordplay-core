package org.syphr.wordplay.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.syphr.wordplay.core.Bag;
import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.NoSuchPieceException;
import org.syphr.wordplay.core.NotEnoughPiecesException;
import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.PieceFactory;

import com.google.common.collect.Multiset;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BagImpl implements Bag
{
    private final Multiset<Letter> letters;
    private final PieceFactory pieceFactory;
    private final RandomGenerator random;

    public BagImpl(Multiset<Letter> letters, PieceFactory pieceFactory)
    {
        this(letters, pieceFactory, new Random());
    }

    @Override
    public boolean isEmpty()
    {
        return letters.isEmpty();
    }

    @Override
    public int getCount()
    {
        return letters.size();
    }

    @Override
    public Piece getPiece() throws NotEnoughPiecesException
    {
        if (isEmpty()) {
            throw new NotEnoughPiecesException("Cannot retrieve a piece from an empty bag");
        }

        int count = getCount();
        int letterIndex = random.nextInt(count);

        Letter letter = letters.toArray(new Letter[count])[letterIndex];

        log.trace("Removing randomly selected next letter: {}", letter);
        letters.remove(letter);
        return pieceFactory.createPiece(letter);
    }

    @Override
    public Piece getPiece(Letter letter) throws NoSuchPieceException
    {
        if (!letters.contains(letter)) {
            throw new NoSuchPieceException("The letter \"" + letter + "\" is not in this bag");
        }

        log.trace("Removing letter: {}", letter);
        letters.remove(letter);
        return pieceFactory.createPiece(letter);
    }

    @Override
    public List<Piece> exchange(Collection<Piece> pieces) throws NotEnoughPiecesException
    {
        int incomingCount = pieces.size();
        log.trace("Exchanging {} piece(s)", incomingCount);

        int bagCount = getCount();
        if (bagCount < incomingCount) {
            throw new NotEnoughPiecesException("Not enough pieces in the bag to exchange");
        }

        List<Piece> newPieces = new ArrayList<Piece>();
        for (int i = 0; i < incomingCount; i++) {
            newPieces.add(getPiece());
        }

        for (Piece piece : pieces) {
            letters.add(piece.getLetter());
        }

        return newPieces;
    }

    @Override
    public void returnPiece(Piece piece)
    {
        letters.add(piece.getLetter());
    }

    @Override
    public void returnPieces(Collection<Piece> pieces)
    {
        for (Piece piece : pieces) {
            returnPiece(piece);
        }
    }
}
