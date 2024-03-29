/*
 * Copyright © 2012-2023 Gregory P. Moyer
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.lang.Dictionary;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;
import org.syphr.wordplay.core.space.Orientations;
import org.syphr.wordplay.core.space.Vector;

@ExtendWith(MockitoExtension.class)
public class BoardImplTest implements WithAssertions
{
    @Mock
    TileSetFactory tileSetFactory;

    @Mock
    WordFactory wordFactory;

    @Mock
    Dictionary dictionary;

    @Mock
    ScoreCalculator scoreCalc;

    @Mock
    TileSet tileSet;

    @BeforeEach
    public void setup()
    {
        lenient().when(tileSetFactory.createTileSet()).thenReturn(tileSet);
    }

    @Test
    public void getTiles_Idempotent()
    {
        when(tileSetFactory.createTileSet()).thenReturn(mock(TileSet.class));

        BoardImpl board = board();
        board.getTiles();
        board.getTiles();

        verify(tileSetFactory, times(1)).createTileSet();
    }

    @Test
    public void getOrientations_Unmodifiable()
    {
        // given
        Set<Orientation> mutableOrientations = new HashSet<>();
        mutableOrientations.add(mock(Orientation.class));

        var board = board(Dimension.of(3, 3), mutableOrientations, Location.at(0, 0));

        // when
        Executable exec = () -> board.getOrientations().clear();

        // then
        assertThrows(UnsupportedOperationException.class, exec);
    }

    @Test
    public void isValid_LocationValid_WordsValid()
    {
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        doReturn(true).when(board).isLocationSetValid(any());
        doReturn(true).when(board).isWordSetValid(any());

        assertTrue(board.isValid(mock(Placement.class)));
    }

    @Test
    public void isValid_LocationInvalid_WordsValid()
    {
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        lenient().doReturn(false).when(board).isLocationSetValid(any());
        lenient().doReturn(true).when(board).isWordSetValid(any());

        assertFalse(board.isValid(mock(Placement.class)));
    }

    @Test
    public void isValid_LocationValid_WordsInvalid()
    {
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        lenient().doReturn(true).when(board).isLocationSetValid(any());
        lenient().doReturn(false).when(board).isWordSetValid(any());

        assertFalse(board.isValid(mock(Placement.class)));
    }

    @Test
    public void isValid_LocationInvalid_WordsInvalid()
    {
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        lenient().doReturn(false).when(board).isLocationSetValid(any());
        lenient().doReturn(false).when(board).isWordSetValid(any());

        assertFalse(board.isValid(mock(Placement.class)));
    }

    @Test
    public void place_LocationInvalid_WordsValid() throws PlacementException
    {
        // given
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        lenient().doReturn(false).when(board).isLocationSetValid(any());
        lenient().doReturn(true).when(board).isWordSetValid(any());

        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(mock(Piece.class)));

        // when
        Executable exec = () -> board.place(placement);

        // then
        assertThrows(InvalidLocationException.class, exec);
    }

    @Test
    public void place_LocationValid_WordsInvalid() throws PlacementException
    {
        // given
        BoardImpl board = spy(board());
        doReturn(new TreeMap<>(Map.of())).when(board).getPieces(any());
        lenient().doReturn(true).when(board).isLocationSetValid(any());
        lenient().doReturn(false).when(board).isWordSetValid(any());

        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(mock(Piece.class)));

        // when
        Executable exec = () -> board.place(placement);

        // then
        assertThrows(InvalidWordException.class, exec);
    }

    @Test
    public void place_LocationValid_WordsValid() throws PlacementException
    {
        Set<Word> words = Set.of();
        when(wordFactory.getWords(any(), any(), any())).thenReturn(words);

        Map<Location, List<TileAttribute>> attributes = Map.of();
        when(tileSet.getAttributes(any())).thenReturn(attributes);

        BoardImpl board = spy(board());
        doReturn(true).when(board).isLocationSetValid(any());
        doReturn(true).when(board).isWordSetValid(any());
        doNothing().when(board).updateTiles(any());

        when(scoreCalc.getScore(any(), any(), any())).thenReturn(1);

        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(mock(Piece.class)));
        int score = board.place(placement);

        assertThat(score).isEqualTo(1);
        verify(scoreCalc).getScore(any(), eq(words), eq(attributes));
    }

    @Test
    public void calculatePoints()
    {
        BoardImpl board = board();

        Piece piece1 = mock(Piece.class);
        Piece piece2 = mock(Piece.class);
        SortedMap<Location, Piece> pieces = new TreeMap<>(Map.of(Location.at(0, 0, 0),
                                                                 piece1,
                                                                 Location.at(1, 0, 0),
                                                                 piece2));

        Tile tile = mock(Tile.class);
        when(tileSet.getTile(any())).thenReturn(tile);

        Set<Word> words = Set.of();
        when(wordFactory.getWords(pieces, Orientations.x(), board)).thenReturn(words);

        Map<Location, List<TileAttribute>> attributes = Map.of();
        when(tileSet.getAttributes(any())).thenReturn(attributes);

        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(piece1, piece2));
        board.calculatePoints(placement);

        verify(scoreCalc).getScore(pieces, words, attributes);
    }

    @Test
    public void getPieces_PlacementX()
    {
        Tile tile = mock(Tile.class);
        when(tileSet.getTile(any())).thenReturn(tile);

        Piece piece1 = mock(Piece.class);
        Piece piece2 = mock(Piece.class);
        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(piece1, piece2));

        Map<Location, Piece> pieces = board().getPieces(placement);

        assertThat(pieces.entrySet()).hasSize(2);
        assertSame(piece1, pieces.get(Location.at(0, 0, 0)));
        assertSame(piece2, pieces.get(Location.at(1, 0, 0)));
    }

    @Test
    public void getPieces_PlacementY()
    {
        Tile tile = mock(Tile.class);
        when(tileSet.getTile(any())).thenReturn(tile);

        Piece piece1 = mock(Piece.class);
        Piece piece2 = mock(Piece.class);
        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.y(), List.of(piece1, piece2));

        Map<Location, Piece> pieces = board().getPieces(placement);

        assertThat(pieces.entrySet()).hasSize(2);
        assertSame(piece1, pieces.get(Location.at(0, 0, 0)));
        assertSame(piece2, pieces.get(Location.at(0, 1, 0)));
    }

    @Test
    public void getPieces_PlacementZ()
    {
        Tile tile = mock(Tile.class);
        when(tileSet.getTile(any())).thenReturn(tile);

        Piece piece1 = mock(Piece.class);
        Piece piece2 = mock(Piece.class);
        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.z(), List.of(piece1, piece2));

        Map<Location, Piece> pieces = board().getPieces(placement);

        assertThat(pieces.entrySet()).hasSize(2);
        assertSame(piece1, pieces.get(Location.at(0, 0, 0)));
        assertSame(piece2, pieces.get(Location.at(0, 0, 1)));
    }

    @Test
    public void getPieces_StepOverOccupiedTiles()
    {
        Tile openTile = mock(Tile.class);
        when(openTile.hasPiece()).thenReturn(false);

        Tile occupiedTile = mock(Tile.class);
        when(occupiedTile.hasPiece()).thenReturn(true);

        when(tileSet.getTile(Location.at(1, 0, 0))).thenReturn(occupiedTile);
        when(tileSet.getTile(Location.at(2, 0, 0))).thenReturn(openTile);

        Piece piece1 = mock(Piece.class);
        Piece piece2 = mock(Piece.class);
        Placement placement = new PlacementImpl(Location.at(0, 0, 0), Orientations.x(), List.of(piece1, piece2));

        Map<Location, Piece> pieces = board().getPieces(placement);

        assertThat(pieces.entrySet()).hasSize(2);
        assertSame(piece1, pieces.get(Location.at(0, 0, 0)));
        assertSame(piece2, pieces.get(Location.at(2, 0, 0)));
    }

    @Test
    public void isLocationSetValid_BoardEdges()
    {
        Location start = Location.at(2, 2, 2);

        BoardImpl board = spy(board(Dimension.of(5, 5, 5), Orientations.xyz(), start));
        doReturn(true).when(board).hasAdjacentPiece(any());

        // start tile is always occupied
        when(tileSet.getTile(any())).thenAnswer(new Answer<Tile>()
        {
            @Override
            public Tile answer(InvocationOnMock invocation) throws Throwable
            {
                Tile tile = mock(Tile.class);

                Location l = invocation.getArgument(0);
                if (start.equals(l)) {
                    when(tile.hasPiece()).thenReturn(true);
                }

                return tile;
            }
        });

        Set<Location> locations = new HashSet<>();

        // lower northwest
        locations.add(Location.at(-1, -1, -1));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(0, 0, 0));
        assertTrue(board.isLocationSetValid(locations));

        // lower northeast
        locations.clear();
        locations.add(Location.at(5, -1, -1));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(4, 0, 0));
        assertTrue(board.isLocationSetValid(locations));

        // lower southwest
        locations.clear();
        locations.add(Location.at(-1, 5, -1));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(0, 4, 0));
        assertTrue(board.isLocationSetValid(locations));

        // lower southeast
        locations.clear();
        locations.add(Location.at(5, 5, -1));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(4, 4, 0));
        assertTrue(board.isLocationSetValid(locations));

        // upper northwest
        locations.clear();
        locations.add(Location.at(-1, -1, 5));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(0, 0, 4));
        assertTrue(board.isLocationSetValid(locations));

        // upper northeast
        locations.clear();
        locations.add(Location.at(5, -1, 5));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(4, 0, 4));
        assertTrue(board.isLocationSetValid(locations));

        // upper southwest
        locations.clear();
        locations.add(Location.at(-1, 5, 5));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(0, 4, 4));
        assertTrue(board.isLocationSetValid(locations));

        // upper southeast
        locations.clear();
        locations.add(Location.at(5, 5, 5));
        assertFalse(board.isLocationSetValid(locations));
        locations.clear();
        locations.add(Location.at(4, 4, 4));
        assertTrue(board.isLocationSetValid(locations));

        // bad x
        locations.clear();
        locations.add(Location.at(-1, 0, 0));
        assertFalse(board.isLocationSetValid(locations));

        // bad y
        locations.clear();
        locations.add(Location.at(0, -1, 0));
        assertFalse(board.isLocationSetValid(locations));

        // bad z
        locations.clear();
        locations.add(Location.at(0, 0, -1));
        assertFalse(board.isLocationSetValid(locations));
    }

    @Test
    public void isLocationSetValid_OpenTile() throws PlacementException
    {
        // start tile is always occupied
        Location start = Location.at(0, 0, 0);
        when(tileSet.getTile(any())).thenAnswer(new Answer<Tile>()
        {
            @Override
            public Tile answer(InvocationOnMock invocation) throws Throwable
            {
                Tile tile = mock(Tile.class);

                Location l = invocation.getArgument(0);
                if (start.equals(l)) {
                    when(tile.hasPiece()).thenReturn(true);
                }

                return tile;
            }
        });

        Set<Location> locations = Set.of(start.move(Vector.of(1, 0, 0)));

        assertTrue(board().isLocationSetValid(locations));
    }

    @Test
    public void isLocationSetValid_OccupiedTile() throws PlacementException
    {
        // start tile is always occupied
        Location start = Location.at(0, 0, 0);
        when(tileSet.getTile(any())).thenAnswer(new Answer<Tile>()
        {
            @Override
            public Tile answer(InvocationOnMock invocation) throws Throwable
            {
                Tile tile = mock(Tile.class);

                Location l = invocation.getArgument(0);
                if (start.equals(l)) {
                    when(tile.hasPiece()).thenReturn(true);
                }

                return tile;
            }
        });

        Set<Location> locations = Set.of(start);

        assertFalse(board().isLocationSetValid(locations));
    }

    @Test
    public void isLocationSetValid_NoStart_NotPlacedOnStart() throws PlacementException
    {
        when(tileSet.getTile(any())).thenReturn(mock(Tile.class));

        Location start = Location.at(0, 0, 0);
        Set<Location> locations = Set.of(start.move(Vector.of(1, 0, 0)));

        assertFalse(board().isLocationSetValid(locations));
    }

    @Test
    public void isLocationSetValid_NoStart_PlacedOnStart() throws PlacementException
    {
        when(tileSet.getTile(any())).thenReturn(mock(Tile.class));

        Location start = Location.at(0, 0, 0);
        Set<Location> locations = Set.of(start);

        assertTrue(board().isLocationSetValid(locations));
    }

    @Test
    public void isLocationSetValid_NoAdjacentPiece() throws PlacementException
    {
        // start tile is always occupied
        Location start = Location.at(0, 0, 0);
        when(tileSet.getTile(any())).thenAnswer(new Answer<Tile>()
        {
            @Override
            public Tile answer(InvocationOnMock invocation) throws Throwable
            {
                Tile tile = mock(Tile.class);

                Location l = invocation.getArgument(0);
                if (start.equals(l)) {
                    when(tile.hasPiece()).thenReturn(true);
                }

                return tile;
            }
        });

        Set<Location> locations = Set.of(start.move(Vector.of(2, 0, 0)));

        assertFalse(board().isLocationSetValid(locations));
    }

    @Test
    public void isWordSetValid_Empty()
    {
        assertFalse(board().isWordSetValid(Set.of()));
    }

    @Test
    public void isWordSetValid_OneWord_Valid()
    {
        Word validWord = mock(Word.class);
        when(validWord.getText()).thenReturn("valid");

        when(dictionary.isValid("valid")).thenReturn(true);

        assertTrue(board().isWordSetValid(Set.of(validWord)));
    }

    @Test
    public void isWordSetValid_OneWord_Invalid()
    {
        Word invalidWord = mock(Word.class);
        when(invalidWord.getText()).thenReturn("invalid");

        when(dictionary.isValid("invalid")).thenReturn(false);

        assertFalse(board().isWordSetValid(Set.of(invalidWord)));
    }

    @Test
    public void isWordSetValid_TwoWords_Valid()
    {
        Word validWord1 = mock(Word.class);
        when(validWord1.getText()).thenReturn("valid");

        Word validWord2 = mock(Word.class);
        when(validWord2.getText()).thenReturn("valid");

        when(dictionary.isValid("valid")).thenReturn(true);

        assertTrue(board().isWordSetValid(Set.of(validWord1, validWord2)));
    }

    @Test
    public void isWordSetValid_TwoWords_Invalid()
    {
        Word validWord = mock(Word.class);
        lenient().when(validWord.getText()).thenReturn("valid");

        Word invalidWord = mock(Word.class);
        lenient().when(invalidWord.getText()).thenReturn("invalid");

        lenient().when(dictionary.isValid("valid")).thenReturn(true);
        lenient().when(dictionary.isValid("invalid")).thenReturn(false);

        assertFalse(board().isWordSetValid(Set.of(validWord, invalidWord)));
    }

    @Test
    public void testHasAdjacentPieceLocationOrientation()
    {
        // start tile is always occupied
        Location start = Location.at(1, 1, 1);
        when(tileSet.getTile(any())).thenAnswer(new Answer<Tile>()
        {
            @Override
            public Tile answer(InvocationOnMock invocation) throws Throwable
            {
                Tile tile = mock(Tile.class);

                Location l = invocation.getArgument(0);
                if (start.equals(l)) {
                    when(tile.hasPiece()).thenReturn(true);
                }

                return tile;
            }
        });

        BoardImpl board = board(Dimension.of(3, 3, 3), Orientations.xyz(), start);

        assertTrue(board.hasAdjacentPiece(Location.at(0, 1, 1), Orientations.x()));
        assertFalse(board.hasAdjacentPiece(Location.at(0, 1, 1), Orientations.y()));
        assertFalse(board.hasAdjacentPiece(Location.at(0, 1, 1), Orientations.z()));

        assertTrue(board.hasAdjacentPiece(Location.at(2, 1, 1), Orientations.x()));
        assertFalse(board.hasAdjacentPiece(Location.at(2, 1, 1), Orientations.y()));
        assertFalse(board.hasAdjacentPiece(Location.at(2, 1, 1), Orientations.z()));

        assertFalse(board.hasAdjacentPiece(Location.at(1, 0, 1), Orientations.x()));
        assertTrue(board.hasAdjacentPiece(Location.at(1, 0, 1), Orientations.y()));
        assertFalse(board.hasAdjacentPiece(Location.at(1, 0, 1), Orientations.z()));

        assertFalse(board.hasAdjacentPiece(Location.at(1, 2, 1), Orientations.x()));
        assertTrue(board.hasAdjacentPiece(Location.at(1, 2, 1), Orientations.y()));
        assertFalse(board.hasAdjacentPiece(Location.at(1, 2, 1), Orientations.z()));

        assertFalse(board.hasAdjacentPiece(Location.at(1, 1, 0), Orientations.x()));
        assertFalse(board.hasAdjacentPiece(Location.at(1, 1, 0), Orientations.y()));
        assertTrue(board.hasAdjacentPiece(Location.at(1, 1, 0), Orientations.z()));

        assertFalse(board.hasAdjacentPiece(Location.at(1, 1, 2), Orientations.x()));
        assertFalse(board.hasAdjacentPiece(Location.at(1, 1, 2), Orientations.y()));
        assertTrue(board.hasAdjacentPiece(Location.at(1, 1, 2), Orientations.z()));
    }

    @Test
    public void updateTiles()
    {
        Location location1 = Location.at(0, 0, 0);
        Piece piece1 = mock(Piece.class);
        Tile tile1 = mock(Tile.class);

        Location location2 = Location.at(1, 0, 0);
        Piece piece2 = mock(Piece.class);
        Tile tile2 = mock(Tile.class);

        when(tileSet.getTile(location1)).thenReturn(tile1);
        when(tileSet.getTile(location2)).thenReturn(tile2);

        SortedMap<Location, Piece> pieces = new TreeMap<>(Map.of(location1, piece1, location2, piece2));
        board().updateTiles(pieces);

        verify(tile1).setPiece(piece1);
        verify(tile2).setPiece(piece2);
    }

    private BoardImpl board()
    {
        return board(Dimension.of(3, 3, 3), Orientations.xyz(), Location.at(0, 0, 0));
    }

    private BoardImpl board(Dimension dimension, Set<Orientation> orientations, Location start)
    {
        return new BoardImpl(dimension, orientations, start, tileSetFactory, wordFactory, dictionary, scoreCalc);
    }
}
