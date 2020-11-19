package fr.epita.tfidf.tokenisation;

public class Stemmer {
    private static final String[] suffixes = {
            "ing",
            "ed"
    };

    public static String stemWord(String word)
    {
        for (String suf : suffixes)
        {
            if (word.endsWith(suf))
            {
                word = word.substring(0, word.length() - suf.length());
                return word;
            }
        }
        return word;
    }
}
