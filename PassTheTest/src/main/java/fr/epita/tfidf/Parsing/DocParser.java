package fr.epita.tfidf.Parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocParser {

    private Document doc = null;

    public DocParser(String url) {
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DocParser(Document document) {
        this.doc = document;
    }

    /*
     * Setters
     */
    public void setDoc(String url){
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
