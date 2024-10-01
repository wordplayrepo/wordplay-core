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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

public class WordFactoryImpl implements WordFactory
{
    @Override
    public Set<Word> getWords(SortedMap<Location, Piece> pieces, Orientation orientation, Board board)
    {
        // TODO why is a tree set used, but an unordered interface is returned?
        Set<Word> words = new TreeSet<>();

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

        SortedSet<Tile> tiles = new TreeSet<>();
        StringBuilder text = new StringBuilder();

        Location wordEnd = wordStart;
        while (true) {
            Tile tile = tileset.getTile(wordEnd);
            tiles.add(tile);

            Optional<Letter> letter = tile.getPiece().orElse(pieces.get(wordEnd)).getLetter();
            text.append(letter.orElseThrow().toString());

            Location newLocation = orientation.move(wordEnd, 1);
            if (!tileset.getTile(newLocation).hasPiece() && !pieces.containsKey(newLocation)) {
                break;
            }

            wordEnd = newLocation;
        }

        // TODO minimum word length should be in the configuration
        if (tiles.size() < 2) {
            return null;
        }

        return new WordImpl(text.toString(), wordStart, wordEnd, orientation, tiles);
    }
}
