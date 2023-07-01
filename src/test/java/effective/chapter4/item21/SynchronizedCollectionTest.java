package effective.chapter4.item21;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

public class SynchronizedCollectionTest {

    @Test
    @DisplayName("2개의 쓰레드가 공유하는 환경에서 한 쓰레드가 removeIf를 호출하면 예외가 발생합니다 - synchronizedCollection")
    void test() {
        // given
        List<Integer> hello = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Collection<Integer> synchronizedCollection = Collections.synchronizedCollection(hello);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // then
        executorService.submit(() -> {
            assertThatThrownBy(() -> synchronizedCollection.removeIf(n -> n % 2 == 0))
                    .isInstanceOf(ConcurrentModificationException.class);
        });
    }
}
