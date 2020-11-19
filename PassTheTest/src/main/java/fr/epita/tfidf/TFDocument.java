package fr.epita.tfidf;

import fr.epita.tfidf.Parsing.DocParser;
import fr.epita.tfidf.tokenisation.Stemmer;
import fr.epita.tfidf.tokenisation.Tokenizer;
import fr.epita.tfidf.vectorisation.Pair;
import fr.epita.tfidf.vectorisation.Vectoriser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TFDocument {

    public Map<String, Pair<Float, List<Integer>>> vectorWord;
    public String fullText;
    private final DocParser docParser;
    private final Tokenizer tokenizer;


    public TFDocument(final String fullText) {
        this.fullText = fullText;

        docParser = new DocParser(fullText);
        tokenizer = new Tokenizer();

        InitDoc();
    }

    private void InitDoc() {
        String textCleaned = docParser.getText();
        List<String> tokens = tokenizer.tokenize(textCleaned);
        List<String> words = new ArrayList<>(tokens);
        Vectoriser vectoriser = new Vectoriser(words);
        vectorWord = vectoriser.vectorise();
    }

}
