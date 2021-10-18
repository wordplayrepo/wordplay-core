package org.syphr.wordplay.core.impl;

import org.syphr.wordplay.core.Letter;
import org.syphr.wordplay.core.Piece;
import org.syphr.wordplay.core.impl.AbstractPiece;

public class TestPiece extends AbstractPiece
{
    private int value;

    public TestPiece()
    {
        super();
    }

    public TestPiece(Letter letter)
    {
        setLetter(letter);
    }

    public TestPiece(Letter letter, boolean wild)
    {
        setLetter(letter);
        setWild(wild);
    }

    @Override
    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    @Override
    public Piece copy()
    {
        TestPiece piece = new TestPiece();
        copyTo(piece);

        return piece;
    }

    protected TestPiece copyTo(TestPiece piece)
    {
        super.copyTo(piece);
        piece.setValue(getValue());

        return piece;
    }
}
