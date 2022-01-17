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
package org.syphr.wordplay.core.config;

import java.util.Map;
import java.util.Set;

import org.syphr.wordplay.core.lang.Dictionary;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;
import org.syphr.wordplay.core.space.Dimension;
import org.syphr.wordplay.core.space.Location;
import org.syphr.wordplay.core.space.Orientation;

public interface Configuration
{
    public Dimension getBoardDimension();

    public Location getBoardStart();

    public Set<Orientation> getOrientations();

    public Set<TileAttribute> getTileAttributes(Location location);

    public int getRackSize();

    public int getRackBonus();

    public LetterFactory getLetterFactory();

    public Dictionary getDictionary();

    public int getLetterCount(Letter letter);

    public int getLetterValue(Letter letter);

    public Object getExtension(String id);

    public Map<String, Object> getExtensions();

    public static ConfigurationBuilder builder()
    {
        return new ConfigurationBuilder();
    }
}