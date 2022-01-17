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

import java.util.UUID;

import org.syphr.wordplay.core.component.Rack;

public class PlayerImpl implements Player
{
    private UUID id;

    private String name;

    private Rack rack;

    private int score;

    private boolean resigned;

    public PlayerImpl()
    {
        this(UUID.randomUUID());
    }

    public PlayerImpl(UUID id)
    {
        this.id = id;
    }

    @Override
    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public UUID getId()
    {
        return id;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setRack(Rack rack)
    {
        this.rack = rack;
    }

    @Override
    public Rack getRack()
    {
        return rack;
    }

    @Override
    public int getScore()
    {
        return score;
    }

    @Override
    public void addScore(int value)
    {
        score += value;
    }

    @Override
    public void resetScore()
    {
        score = 0;
    }

    @Override
    public void resign()
    {
        resigned = true;
    }

    @Override
    public boolean isResigned()
    {
        return resigned;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        PlayerImpl other = (PlayerImpl) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
