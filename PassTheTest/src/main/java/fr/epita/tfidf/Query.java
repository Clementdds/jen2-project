package fr.epita.tfidf;

import fr.epita.tfidf.tokenisation.Tokenizer;
import fr.epita.tfidf.vectorisation.Vectoriser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Query {

    private final Indexer _indexer;
    private final Tokenizer _tokenizer;
    private final Vectoriser _vectorizer;

    private Map<String, Double> idf;
    private Map<TFDocument, Map<String, Double>> tfidf;

    public Query(final Indexer indexer, final Tokenizer tokenizer, final Vectoriser vectoriser)
    {
        _indexer = indexer;
        _tokenizer = tokenizer;
        _vectorizer = vectoriser;
        idf = new HashMap<>();
        tfidf = new HashMap<>();
    }

    public List<Double> normalize(List<Double> w2v){
        Double norm = 0.0;
        for(Double x: w2v){
            norm += x*x;
        }
        norm = Math.sqrt(norm);
        for(Double x: w2v){
            x = x/norm;
        }
        return w2v;
    }

    private void computeIdf()
    {
        for (Map.Entry<String, List<TFDocument>> entry : _indexer.dict.entrySet())
        {
            idf.put(entry.getKey(),
                    Math.log((double)_indexer.docs.size() / (1 + entry.getValue().size())));
        }
    }

    public List<TFDocument> request(String query)
    {
        List<String> queryTokens = _tokenizer.tokenize(query);
        var queryVector = _vectorizer.vectorise(queryTokens);
        computeIdf();
        for (TFDocument doc : _indexer.docs)
        {
            Map<String, Double> vector = tfidf.put(doc, new HashMap<>());
            var words = doc.vectorWord;
            for (var entry : words.entrySet())
            {
                var kw = entry.getKey();
                var kwidf = idf.get(kw);
                var kwfq = entry.getValue().left;
                vector.put(kw, kwidf * kwfq);
            }
            List<Double> valueVector = new ArrayList<>(vector.values());
        }
    }
}
