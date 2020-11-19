package fr.epita.tfidf;

import java.util.List;

public class TFDocument {

    final List<String> words;
    final String fullText;

    public TFDocument(final String fullText, final List<String> words) {
        this.words = words;
        this.fullText = fullText;
    }

}
