package org.syphr.wordplay.core.player;

import org.syphr.wordplay.core.component.AbstractPiece;
import org.syphr.wordplay.core.component.Piece;
import org.syphr.wordplay.core.lang.Letter;

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
