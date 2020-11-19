package fr.epita.hivers;

import fr.epita.tfidf.Parsing.DocParser;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import org.jsoup.nodes.Document;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocParserTest {

    @Test
    public void ParseHtmlFromUrl() {
        DocParser docParser = new DocParser("<p>An <a href='http://example.com/'><b>example</b></a> link.</p>");
        String text = docParser.getText();

        assertEquals(
                "An example link.",
                text);
    }

    @Test
    public void ParseHtmlFromDocument() {
        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
        Document document = Jsoup.parse(html);

        DocParser docParser = new DocParser(document);
        String text = docParser.getText();

        assertEquals("An example link.", text);
    }
}
