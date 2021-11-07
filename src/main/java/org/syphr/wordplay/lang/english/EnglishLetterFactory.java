package org.syphr.wordplay.lang.english;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.syphr.wordplay.core.lang.Letter;
import org.syphr.wordplay.core.lang.LetterFactory;

public class EnglishLetterFactory implements LetterFactory
{
    @Override
    public Letter toLetter(char character)
    {
        return EnglishLetter.valueOf(String.valueOf(character).toUpperCase());
    }

    @Override
    public Letter toLetter(String character)
    {
        if (character == null || character.isEmpty())
        {
            return null;
        }

        return toLetter(character.charAt(0));
    }

    @Override
    public List<Letter> toLetters(String word)
    {
        List<Letter> letters = new LinkedList<Letter>();

        if (word != null)
        {
            for (char c : word.toCharArray())
            {
                letters.add(toLetter(c));
            }
        }

        return letters;
    }

    @Override
    public Set<Letter> getLetters()
    {
        return new LinkedHashSet<Letter>(Arrays.asList(EnglishLetter.values()));
    }
}
