package org.syphr.wordplay.lang.english;

import org.syphr.wordplay.core.lang.Letter;

public enum EnglishLetter implements Letter
{
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    @Override
    public char getCharacter()
    {
        return name().charAt(0);
    }
}
