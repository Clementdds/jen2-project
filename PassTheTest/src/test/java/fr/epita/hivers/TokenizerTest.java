package fr.epita.hivers;

import fr.epita.tfidf.tokenisation.Tokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class TokenizerTest {

    @Test
    public void TokenizeBasicString() {
        Tokenizer tokenizer = new Tokenizer();
        List<String> result = tokenizer.tokenize("This is an example link");
        List<String> expected = Arrays.asList("example", "link");
        Assertions.assertEquals(result, expected);
    }
}
