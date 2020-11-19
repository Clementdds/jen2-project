package fr.epita.hivers;

import fr.epita.tfidf.vectorisation.Pair;
import fr.epita.tfidf.vectorisation.Vectoriser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorizeTest {

    @Test
    public void testBasicVectorise(){
        List<String> testList = Arrays.asList("blue", "rabbit", "fish", "blue", "river");
        Vectoriser vectoriser = new Vectoriser(testList);

        Map<String, Pair<Float, List<Integer>>> testMap = new HashMap<>();
        testMap.put("blue", new Pair<>(0.4f, Arrays.asList(0, 3)));
        testMap.put("rabbit", new Pair<>(0.4f, Arrays.asList(1)));
        testMap.put("fish", new Pair<>(0.4f, Arrays.asList(2)));
        testMap.put("river", new Pair<>(0.4f, Arrays.asList(4)));

        Map<String, Pair<Float, List<Integer>>> resultMap = vectoriser.vectorise();
        assertEquals(testMap, resultMap);
    }
}
