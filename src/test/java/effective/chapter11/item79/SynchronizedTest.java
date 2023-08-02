package effective.chapter11.item79;

import effective.chapter11.item79.ObservableSet;
import effective.chapter11.item79.SetObserver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

public class SynchronizedTest {

    @Test
    @DisplayName("동기화 블록 안에서 외계인 메소드를 호출한다 - 잘못된 코드")
    void test1() {
        // given
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    s.removeObserver(this);
                }
            }
        });

        // expected
        assertThatThrownBy(() -> set.add(23))
                .isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    @DisplayName("쓸데없이 백그라운드 스레드를 사용하는 관찰자")
    void test2() {
        // given
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService exec =
                            Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(() -> s.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });
    }
}
