package fr.epita.tfidf;

import fr.epita.tfidf.vectorisation.Pair;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Query {

    private final Indexer _indexer;

    private final Map<String, Double> idf;
    private final Map<TFDocument, Map<String, Double>> tfidf;

    public Query(final Indexer indexer)
    {
        _indexer = indexer;
        idf = new HashMap<>();
        tfidf = new HashMap<>();
    }

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
        // Vector normalise of docs in index
        for (TFDocument doc : _indexer.docs)
        {
            Map<String, Double> vector = new HashMap<>();
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
        // TF Doc query idf
        TFDocument queryDoc = new TFDocument(query);
        var queryWords = queryDoc.vectorWord;
        Map<String, Double> queryVector = new HashMap<>();
        for (var entry : queryWords.entrySet())
        {
            var kw = entry.getKey();
            var kwidf = idf.get(kw);
            var kwfq = entry.getValue().left;
            queryVector.put(kw, kwidf * kwfq);
        }
        Map<String, Double> normalizeQueryVector = normalize(queryVector);

        computeIdf();
        computeTfIdf();

        List<Pair<TFDocument, Double>> cosineList = new ArrayList<>();
        for (var /* Map<String, Double> vectorized */ docVector : tfidf.entrySet()) {
            double cos = 0;
            for (var word : normalizeQueryVector.entrySet())
            {
                String kw = word.getKey();
                if (!docVector.getValue().containsKey(kw))
                    continue;
                cos += word.getValue() * docVector.getValue().get(kw);
            }
            cosineList.add(new Pair<>(docVector.getKey(), cos));
        }

        cosineList.sort((p1, p2) -> - Double.compare(p1.right, p2.right));

        return cosineList.stream()
                .map(e -> e.left)
                .collect(Collectors.toList());
    }
}
