import com.mti.hivers.impl.provider.Prototype;
import com.mti.hivers.impl.provider.Singleton;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HiversTest {

    public interface TestService {
        void ping();
    }
    public static class TestServiceImpl implements TestService {
        @Override public void ping() { System.out.println("ping"); }
    }

    public static class Nested {
        private final TestService testService;

        public Nested(final TestService testService) {
            this.testService = testService;
        }
    }

    @Test
    public void testBasicHiversUserCase() {

        final var hivers = new Hivers();

        hivers.addProvider(new Singleton<>(TestService.class, new TestServiceImpl()));
        hivers.addProvider(new Singleton<>(TestService.class, TestServiceImpl::new));
        hivers.addProvider(new Prototype<>(Nested.class, () -> new Nested(hivers.instanceOfOrThrow(TestService.class))));
        hivers.addProvider(new Singleton<>(Nested.class, new Nested(hivers.instanceOfOrThrow(TestService.class))));

        final var service = hivers.instanceOf(TestService.class).orElseThrow();
        IntStream.of(5).forEach(i -> service.ping());
    }

}