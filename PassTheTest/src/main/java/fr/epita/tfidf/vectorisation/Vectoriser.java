package fr.epita.tfidf.vectorisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vectoriser {

    private final List<String> stringList;
    private final Map<String, Pair<Float, List<Integer>>> map = new HashMap<>();

    public Vectoriser(List<String> stringList) {
        this.stringList = stringList;
    }

    public Map<String, Pair<Float, List<Integer>>> vectorise(){
        float increment = 1f / stringList.size();

        for (int i = 0; i < stringList.size(); i++){
            String word = stringList.get(i);

            if (this.map.containsKey(word)){
                Pair<Float, List<Integer>> pair = map.get(word);
                pair.left += increment;
                pair.right.add(i);
                this.map.replace(word, pair);
            }
            else {
                List<Integer> list = new ArrayList<>();
                list.add(i);

                this.map.put(word, new Pair<>(increment, list));
            }
        }
        return this.map;
    }

    public Map<String, Pair<Float, List<Integer>>> getMap(){
        return this.map;
    }
}
