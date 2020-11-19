package fr.epita.tfidf.Parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocParser {

    private Document doc = null;

    public DocParser(String fullText) {
        doc = Jsoup.parse(fullText);
    }

    public DocParser(Document document) {
        this.doc = document;
    }

    /*
     * Setters
     */
    public void setDoc(String fullText){
        doc = Jsoup.parse(fullText);
    }

    public void setDoc(Document document) {
        this.doc = document;
    }

    /*
     * Getters
     */
    public Document getDoc(){
        return this.doc;
    }

    public String getText(){
        return this.doc.body().text();
    }

}
