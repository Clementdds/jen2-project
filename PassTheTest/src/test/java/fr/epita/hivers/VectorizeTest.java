package fr.epita.hivers;

import fr.epita.tfidf.vectorisation.Pair;
import fr.epita.tfidf.vectorisation.Vectoriser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VectorizeTest {

    @Test
    public void testBasicVectorise(){
        List<String> testList = Arrays.asList("blue", "rabbit", "fish", "blue", "river");
        Vectoriser vectoriser = new Vectoriser(testList);

        Map<String, Pair<Float, List<Integer>>> map = vectoriser.vectorise();
        System.out.print(map);
    }
}
