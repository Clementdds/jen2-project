package fr.epita.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {

    public final List<TFDocument> docs;
    public final Map<String, List<TFDocument>> dict;

    public Indexer() {
        dict = new HashMap<>();
        docs = new ArrayList<>();
    }

    public void addDocument(TFDocument document) {
        docs.add(document);
        for (String word :  document.vectorWord.keySet()) {
            List<TFDocument> list;
            if (dict.containsKey(word)) {
                list = dict.get(word);
                list.add(document);
            } else {
                list = new ArrayList<>();
            }
            dict.put(word, list);
        }
    }


}
