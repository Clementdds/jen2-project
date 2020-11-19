package fr.epita.tfidf.vectorisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vectoriser {

    public static Map<String, Pair<Float, List<Integer>>> vectorise(List<String> stringList){
        float increment = 1f / stringList.size();
        Map<String, Pair<Float, List<Integer>>> map = new HashMap<>();

        for (int i = 0; i < stringList.size(); i++){
            String word = stringList.get(i);

            if (map.containsKey(word)){
                Pair<Float, List<Integer>> pair = map.get(word);
                pair.left += increment;
                pair.right.add(i);
                map.replace(word, pair);
            }
            else {
                List<Integer> list = new ArrayList<>();
                list.add(i);

                map.put(word, new Pair<>(increment, list));
            }
        }
        return map;
    }
}
