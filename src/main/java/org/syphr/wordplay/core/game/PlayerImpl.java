package org.syphr.wordplay.core.game;

import java.io.IOException;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.syphr.wordplay.core.SchemaVersion;
import org.syphr.wordplay.core.UnsupportedSchemaVersionException;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.component.Rack;
import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.xml.JaxbFactory;

public class PlayerImpl implements Player
{
    private static final JaxbFactory<org.syphr.wordplay.xsd.v1.PlayerType> PLAYER_FACTORY_V1 = JaxbFactory.create(org.syphr.wordplay.xsd.v1.PlayerType.class,
                                                                                                                  new QName(SchemaVersion._1.getStateNamespace(),
                                                                                                                            "player"));

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
    public String serialize(SchemaVersion version) throws UnsupportedSchemaVersionException
    {
        try {
            switch (version) {
                case _1:
                    org.syphr.wordplay.xsd.v1.PlayerType playerTypeV1 = new org.syphr.wordplay.xsd.v1.PlayerType();
                    serialize(playerTypeV1);

                    return PLAYER_FACTORY_V1.write(playerTypeV1);

                default:
                    throw new UnsupportedSchemaVersionException(version);
            }
        } catch (IOException e) {
            throw new UnsupportedSchemaVersionException(version, e);
        }
    }

    protected void serialize(org.syphr.wordplay.xsd.v1.PlayerType playerType)
    {
        playerType.setId(getId().toString());
        playerType.setName(getName());
        playerType.setResigned(isResigned());
        playerType.setScore(getScore());

        org.syphr.wordplay.xsd.v1.RackSnapshotType rack = new org.syphr.wordplay.xsd.v1.RackSnapshotType();
        for (Piece piece : getRack().getPieces()) {
            org.syphr.wordplay.xsd.v1.PieceType pieceType = new org.syphr.wordplay.xsd.v1.PieceType();
            pieceType.setWild(piece.isWild());

            Letter letter = piece.getLetter();
            if (letter != null) {
                pieceType.setLetter(letter.toString());
            }

            rack.getPieces().add(pieceType);
        }
        playerType.setRack(rack);

        /*
         * A no-arg constructor is required.
         */
        org.syphr.wordplay.xsd.v1.ClassType classType = new org.syphr.wordplay.xsd.v1.ClassType();
        classType.setType(getClass().getName());
        playerType.setType(classType);
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
