package effective.chapter9.item59;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.*;

public class LibraryTest {

    @Test
    @DisplayName("random 메소드가 약 50만 개가 아니라 666666에 가까운 값을 얻는다 - 잘못 구현")
    void test1() {
        // given
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;

        // when
        for (int i = 0; i < 1000000; i++) {
            if (DirectImplementation.random(n) < n / 2) {
                low++;
            }
        }

        // then
        assertThat(low).isGreaterThan(660000);
        assertThat(low).isLessThan(670000);
    }

    @Test
    @DisplayName("이미 구현된 라이브러리가 존재한다면 적극 사용하자")
    void test2() {
        // given
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        ThreadLocalRandom current = ThreadLocalRandom.current();

        // when
        for (int i = 0; i < 1000000; i++) {
            if (current.nextInt(n) < n / 2) {
                low++;
            }
        }

        // then
        assertThat(low).isGreaterThan(490000);
        assertThat(low).isLessThan(510000);
    }
}
