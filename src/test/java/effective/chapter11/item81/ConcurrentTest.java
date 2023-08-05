package effective.chapter11.item81;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentTest {

    @Test
    @DisplayName("ConcurrentMap으로 구현한 동시성 정규화 맵 - 최적은 아니다")
    void test1() {
        // given
        Map<String, String> map = new ConcurrentHashMap<>();
        String s = "hello";

        // when
        String result1 = intern1(map, s);
        String result2 = intern1(map, s);

        // then
        assertThat(result1).isEqualTo("hello");
        assertThat(result2).isEqualTo("hello");
    }

    @Test
    @DisplayName("ConcurrentMap으로 구현한 동시성 정규화 맵 - 더 빠르다!")
    void test2() {
        // given
        Map<String, String> map = new ConcurrentHashMap<>();
        String s = "hello";

        // when
        String result1 = intern2(map, s);
        String result2 = intern2(map, s);

        // then
        assertThat(result1).isEqualTo("hello");
        assertThat(result2).isEqualTo("hello");
    }

    private static String intern1(Map<String, String> map, String s) {
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s : previousValue;
    }

    private static String intern2(Map<String, String> map, String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null) {
                result = s;
            }
        }
        return result;
    }
}
