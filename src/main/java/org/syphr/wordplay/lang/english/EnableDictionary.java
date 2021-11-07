package org.syphr.wordplay.lang.english;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.syphr.wordplay.core.lang.Dictionary;

public class EnableDictionary implements Dictionary
{
    private static final String DICTIONARY_RESOURCE = "enable-dictionary.txt";

    private static final Set<String> WORDS;
    static
    {
        WORDS = new HashSet<String>();

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(EnableDictionary.class.getResourceAsStream(DICTIONARY_RESOURCE)));
            try
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    WORDS.add(line);
                }
            }
            finally
            {
                reader.close();
            }
        }
        catch (IOException e)
        {
            throw new IllegalStateException("ENABLE dictionary resource is missing!");
        }
    }

    @Override
    public boolean isValid(String word)
    {
        if (word == null || word.length() < 2)
        {
            return false;
        }

        return WORDS.contains(word.toLowerCase());
    }

    @Override
    public Set<String> getWords()
    {
        return Collections.unmodifiableSet(WORDS);
    }

    @Override
    public String toString()
    {
        return "ENABLE";
    }
}
