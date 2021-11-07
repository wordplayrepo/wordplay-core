package org.syphr.wordplay.core.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.syphr.wordplay.core.Board;
import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.Tile;
import org.syphr.wordplay.core.TileSet;
import org.syphr.wordplay.core.Word;
import org.syphr.wordplay.core.WordFactory;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

public class WordFactoryImpl implements WordFactory
{
    @Override
    public Set<Word> getWords(SortedMap<Location, Piece> pieces, Orientation orientation, Board board)
    {
        // TODO why is a tree set used, but an unordered interface is returned?
        Set<Word> words = new TreeSet<Word>();

        Word mainWord = getWord(pieces.keySet().iterator().next(), orientation, board.getTiles(), pieces);
        if (mainWord != null) {
            words.add(mainWord);
        }

        Set<Orientation> allowedOrientations = new HashSet<>(board.getOrientations());
        allowedOrientations.remove(orientation);
        for (Orientation altOrientation : allowedOrientations) {
            for (Location location : pieces.keySet()) {
                Word word = getWord(location, altOrientation, board.getTiles(), pieces);
                if (word != null) {
                    words.add(word);
                }
            }
        }

        return words;
    }

    private Word getWord(Location startLocation,
                         Orientation orientation,
                         TileSet tileset,
                         SortedMap<Location, Piece> pieces)
    {
        Location wordStart = startLocation;
        while (true) {
            Location newLocation = orientation.move(wordStart, -1);
            if (!tileset.getTile(newLocation).hasPiece() && !pieces.containsKey(newLocation)) {
                break;
            }

            wordStart = newLocation;
        }

        SortedSet<Tile> tiles = new TreeSet<Tile>();
        StringBuilder text = new StringBuilder();

        Location wordEnd = wordStart;
        while (true) {
            Tile tile = tileset.getTile(wordEnd);
            tiles.add(tile);

            Letter letter = tile.hasPiece() ? tile.getPiece().getLetter() : pieces.get(wordEnd).getLetter();
            text.append(letter.toString());

            Location newLocation = orientation.move(wordEnd, 1);
            if (!tileset.getTile(newLocation).hasPiece() && !pieces.containsKey(newLocation)) {
                break;
            }

            wordEnd = newLocation;
        }

        if (tiles.size() < 2) {
            return null;
        }

        return new WordImpl(text.toString(), wordStart, wordEnd, orientation, tiles);
    }
}
