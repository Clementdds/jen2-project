package fr.epita.tfIdf;

import fr.epita.tfidf.Indexer;
import fr.epita.tfidf.Query;
import fr.epita.tfidf.TFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TfIdfTest {

    @Test
    public void testTfIdf(){
        Indexer indexer = new Indexer();
        TFDocument doc1 = new TFDocument("<p>TOTO</p>");
        TFDocument doc2 = new TFDocument("<p>Oui</p>");
        TFDocument doc3 = new TFDocument("<p>Java cool</p>");
        TFDocument doc4 = new TFDocument("<p>Java est</p>");
        TFDocument doc5 = new TFDocument("<p>Java</p>");

        Query query = new Query(indexer);

        query.addDocument(doc1);
        query.addDocument(doc2);
        query.addDocument(doc3);
        query.addDocument(doc4);
        query.addDocument(doc5);

        List<TFDocument> result = query.request("Toto");
        Assertions.assertSame(result.get(0), doc1);

        result = query.request("cool");
        Assertions.assertSame(result.get(0), doc3);

        result = query.request("est");
        Assertions.assertSame(result.get(0), doc4);

        result = query.request("oui");
        Assertions.assertSame(result.get(0), doc2);

        result = query.request("java");
        Assertions.assertSame(result.get(0), doc5);
    }
}
