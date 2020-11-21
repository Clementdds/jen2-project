package fr.epita.tfidf.tokenisation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Tokenizer {
    private Set<String> stopWords;

    public Tokenizer() {
        try {
            loadStopWords("stopwords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isAlphanumerical(char c)
    {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    private void loadStopWords(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null)
            throw new RuntimeException("stop words not found");
        stopWords = new HashSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null){
            stopWords.add(line);
        }
    }

    public List<String> tokenize(String text)
    {
        text = text.toLowerCase();
        List<String> output = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Synonym syn = new Synonym();
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (isAlphanumerical(c)) {
                sb.append(c);
            }
            else
            {
                if (sb.length() == 0)
                    continue;
                String word = sb.toString();
                if (!stopWords.contains(word)) {
                    word = Stemmer.stemWord(word);
                    word = syn.getSynonym(word);
                    output.add(word);
                }
                sb.setLength(0);
            }
        }
        if (sb.length() > 0 && !stopWords.contains(sb.toString())){
            output.add(syn.getSynonym(Stemmer.stemWord(sb.toString())));
        }
        return output;
    }

    public void dump() {
        System.out.println(stopWords.toString());
    }
}
