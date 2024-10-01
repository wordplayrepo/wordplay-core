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

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.syphr.wordplay.core.config.TileAttribute;
import org.syphr.wordplay.core.space.Location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TileImpl implements Tile
{
    @NonNull
    @EqualsAndHashCode.Include
    @Getter
    private final Location location;

    private Piece piece;

    private final Set<TileAttribute> attributes = new HashSet<>();

    protected TileImpl(@NonNull Location location, Piece piece)
    {
        this.location = location;
        this.piece = piece;
    }

    @Override
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    @Override
    public Optional<Piece> getPiece()
    {
        return Optional.ofNullable(piece);
    }

    @Override
    public boolean hasPiece()
    {
        return piece != null;
    }

    @Override
    public int getBaseValue()
    {
        return piece == null ? 0 : piece.getValue();
    }

    @Override
    public void addAttribute(TileAttribute attribute)
    {
        attributes.add(attribute);
    }

    @Override
    public void addAttributes(Collection<TileAttribute> attributes)
    {
        this.attributes.addAll(attributes);
    }

    @Override
    public void removeAttribute(TileAttribute attribute)
    {
        attributes.remove(attribute);
    }

    @Override
    public Set<TileAttribute> getAttributes()
    {
        return Set.copyOf(attributes);
    }

    @Override
    public int compareTo(Tile o)
    {
        return location.compareTo(o.getLocation());
    }
}
