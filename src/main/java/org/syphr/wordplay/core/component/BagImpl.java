/*
 * Copyright © 2012-2024 Gregory P. Moyer
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
package org.syphr.wordplay.core.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.syphr.wordplay.core.lang.Letter;

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

        List<Piece> newPieces = new ArrayList<>();
        for (int i = 0; i < incomingCount; i++) {
            newPieces.add(getPiece());
        }

        pieces.stream().map(Piece::getLetter).map(this::unwrap).forEach(letters::add);

        return newPieces;
    }

    @Override
    public void returnPiece(Piece piece)
    {
        letters.add(unwrap(piece.getLetter()));
    }

    @Override
    public void returnPieces(Collection<Piece> pieces)
    {
        for (Piece piece : pieces) {
            returnPiece(piece);
        }
    }

    private Letter unwrap(Optional<Letter> opt)
    {
        return opt.orElse(null);
    }
}
