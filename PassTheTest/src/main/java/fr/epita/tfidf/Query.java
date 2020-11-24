package fr.epita.tfidf;

import fr.epita.tfidf.tokenisation.Tokenizer;
import fr.epita.tfidf.vectorisation.Vectoriser;

import java.util.*;

public class Query {

    private final Indexer _indexer;
    private final Tokenizer _tokenizer;

    private final Map<String, Double> idf;
    private final Map<TFDocument, Map<String, Double>> tfidf;

    public Query(final Indexer indexer, final Tokenizer tokenizer)
    {
        _indexer = indexer;
        _tokenizer = tokenizer;
        idf = new HashMap<>();
        tfidf = new HashMap<>();
    }

    /*
    public List<Double> normalize(List<Double> w2v){
        double norm = 0.0;
        for(Double x: w2v){
            norm += x*x;
        }
        norm = Math.sqrt(norm);
        for(Double x: w2v){
            x = x/norm;
        }
        return w2v;
    }
    */

    public Double getNorm(Collection<Double> w2v){
        double norm = 0.0;
        for(Double x: w2v){
            norm += x*x;
        }
        return Math.sqrt(norm);
    }

    public Map<String, Double> normalize(Map<String, Double> vector){
        double norm =  getNorm(vector.values());
        Map<String, Double> normalizeVector = new HashMap<>();

        for (var entry : vector.keySet()){
            normalizeVector.put(entry, vector.get(entry)/norm);
        }

        return normalizeVector;
    }

    private void computeIdf()
    {
        for (Map.Entry<String, List<TFDocument>> entry : _indexer.dict.entrySet())
        {
            idf.put(entry.getKey(),
                    Math.log(((double)_indexer.docs.size() / (1 + entry.getValue().size())) + 1));
        }
    }

    private void computeTfIdf(){
        for (TFDocument doc : _indexer.docs)
        {
            Map<String, Double> vector = new HashMap<>(); /*tfidf.put(doc, new HashMap<>());*/
            var words = doc.vectorWord;
            for (var entry : words.entrySet())
            {
                var kw = entry.getKey();
                var kwidf = idf.get(kw);
                var kwfq = entry.getValue().left;
                vector.put(kw, kwidf * kwfq);
            }
            Map<String, Double> normalizeVector = normalize(vector);
            tfidf.put(doc, normalizeVector);
        }
    }

    public List<TFDocument> request(String query)
    {
        //List<String> queryTokens = _tokenizer.tokenize(query);

        computeIdf();
        computeTfIdf();



        return null; //FIXME
    }
}
