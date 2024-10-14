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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientations;

@ExtendWith(MockitoExtension.class)
class WordFactoryImplTest
{
    @Test
    void getWords_NoPieces()
    {
        // given
        var pieces = new TreeMap<Location, Piece>();
        var orientation = Orientations.x();
        var board = mock(Board.class);

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getWords_OnePiece_TooShort()
    {
        // given
        var pieces = new TreeMap<Location, Piece>(Map.of(Location.at(1, 1), piece(letter('a'))));
        var orientation = Orientations.x();
        var board = mock(Board.class);

        var tileset = mock(TileSet.class);
        var tile = mock(Tile.class);
        when(board.getTiles()).thenReturn(tileset);
        when(tileset.getTile(any())).thenReturn(tile);
        when(tile.hasPiece()).thenReturn(false);

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).isEmpty();;
    }

    @Test
    void getWords_TwoPieces_EmptyBoard()
    {
        // given
        var pieces = new TreeMap<Location, Piece>(Map.of(Location.at(1, 1),
                                                         piece(letter('a')),
                                                         Location.at(2, 1),
                                                         piece(letter('b'))));
        var orientation = Orientations.x();
        var board = mock(Board.class);

        var tileset = mock(TileSet.class);
        when(board.getTiles()).thenReturn(tileset);
        when(tileset.getTile(any())).thenAnswer(i -> emptyTile(i.getArgument(0)));

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).hasSize(1)
                          .extracting(Word::getText, Word::getStartLocation, Word::getEndLocation, Word::getOrientation)
                          .containsExactly(Tuple.tuple("ab", Location.at(1, 1), Location.at(2, 1), Orientations.x()));
    }

    @Test
    void getWords_OnePiece_BoardHasOnePieceBehindStartLocation()
    {
        // given
        var pieces = new TreeMap<Location, Piece>(Map.of(Location.at(1, 1), piece(letter('a'))));
        var orientation = Orientations.x();
        var board = mock(Board.class);

        var tileset = mock(TileSet.class);
        when(board.getTiles()).thenReturn(tileset);
        when(board.getOrientations()).thenReturn(Orientations.xy());
        when(tileset.getTile(any())).thenAnswer(i -> emptyTile(i.getArgument(0)));
        when(tileset.getTile(Location.at(0, 1))).thenAnswer(i -> tile(i.getArgument(0), piece(letter('b'))));

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).hasSize(1)
                          .extracting(Word::getText, Word::getStartLocation, Word::getEndLocation, Word::getOrientation)
                          .containsExactly(Tuple.tuple("ba", Location.at(0, 1), Location.at(1, 1), Orientations.x()));
    }

    @Test
    void getWords_OnePiece_BoardHasOnePieceAheadOfStartLocation()
    {
        // given
        var pieces = new TreeMap<Location, Piece>(Map.of(Location.at(1, 1), piece(letter('a'))));
        var orientation = Orientations.x();
        var board = mock(Board.class);

        var tileset = mock(TileSet.class);
        when(board.getTiles()).thenReturn(tileset);
        when(board.getOrientations()).thenReturn(Orientations.xy());
        when(tileset.getTile(any())).thenAnswer(i -> emptyTile(i.getArgument(0)));
        when(tileset.getTile(Location.at(2, 1))).thenAnswer(i -> tile(i.getArgument(0), piece(letter('b'))));

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).hasSize(1)
                          .extracting(Word::getText, Word::getStartLocation, Word::getEndLocation, Word::getOrientation)
                          .containsExactly(Tuple.tuple("ab", Location.at(1, 1), Location.at(2, 1), Orientations.x()));
    }

    @Test
    void getWords_OnePiece_BoardHasOnePieceAheadOfAndOnePieceBelowStartLocation()
    {
        // given
        var pieces = new TreeMap<Location, Piece>(Map.of(Location.at(1, 1), piece(letter('a'))));
        var orientation = Orientations.x();
        var board = mock(Board.class);

        var tileset = mock(TileSet.class);
        when(board.getTiles()).thenReturn(tileset);
        when(board.getOrientations()).thenReturn(Orientations.xy());
        when(tileset.getTile(any())).thenAnswer(i -> emptyTile(i.getArgument(0)));
        when(tileset.getTile(Location.at(2, 1))).thenAnswer(i -> tile(i.getArgument(0), piece(letter('b'))));
        when(tileset.getTile(Location.at(1, 2))).thenAnswer(i -> tile(i.getArgument(0), piece(letter('c'))));

        // when
        Set<Word> result = new WordFactoryImpl().getWords(pieces, orientation, board);

        // then
        assertThat(result).hasSize(2)
                          .extracting(Word::getText, Word::getStartLocation, Word::getEndLocation, Word::getOrientation)
                          .containsExactlyInAnyOrder(Tuple.tuple("ab",
                                                                 Location.at(1, 1),
                                                                 Location.at(2, 1),
                                                                 Orientations.x()),
                                                     Tuple.tuple("ac",
                                                                 Location.at(1, 1),
                                                                 Location.at(1, 2),
                                                                 Orientations.y()));
    }

    private Piece piece(Letter letter)
    {
        var piece = mock(Piece.class);
        lenient().when(piece.getLetter()).thenReturn(Optional.ofNullable(letter));

        return piece;
    }

    private Letter letter(char character)
    {
        return new Letter()
        {
            @Override
            public char getCharacter()
            {
                return character;
            }
        };
    }

    private Tile emptyTile(Location l)
    {
        var tile = tile(l);
        lenient().when(tile.hasPiece()).thenReturn(false);
        lenient().when(tile.getPiece()).thenReturn(Optional.empty());

        return tile;
    }

    private Tile tile(Location l, Piece p)
    {
        var tile = tile(l);
        lenient().when(tile.hasPiece()).thenReturn(true);
        lenient().when(tile.getPiece()).thenReturn(Optional.of(p));

        return tile;
    }

    private Tile tile(Location l)
    {
        var tile = mock(Tile.class);
        lenient().when(tile.getLocation()).thenReturn(l);
        lenient().when(tile.compareTo(any())).thenAnswer(i -> l.compareTo(i.getArgument(0, Tile.class).getLocation()));

        return tile;
    }
}
