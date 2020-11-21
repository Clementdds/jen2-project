package fr.epita.tfidf.tokenisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Synonym {
    private class Entry {
        public String reference;
        public Set<String> others;

        private boolean hasWord(String word) { return others.contains(word) || word.equals(reference);}
        public String getReferenceFromWord(String word) {
            if (hasWord(word))
                return reference;
            return null;
        }
    }

    private List<Entry> entries;

    public Synonym() {
        try {
            loadSynonyms("synonyms.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSynonyms(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null)
            throw new RuntimeException("synonyms not found");
        entries = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null){
            String[] words = line.split(", ");
            if (words.length == 0 || words[0].isEmpty())
                continue;
            var entry = new Entry();
            entry.reference = words[0];
            entry.others = new HashSet<>();
            for (int i = 1; i < words.length; i++)
            {
                if (!words[i].isEmpty())
                    entry.others.add(words[i]);
            }
            entries.add(entry);
        }
    }

    public String getSynonym(String word)
    {
        for (Entry e : entries)
        {
            String tmp = e.getReferenceFromWord(word);
            if (tmp == null)
                continue;
            return tmp;
        }
        return word;
    }
}
