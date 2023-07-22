package effective.chapter7.item48;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ParallelPiTest {

    @Test
    @DisplayName("소수 계산 스트림 파이프라인 - 병렬화 비교")
    void test1() {
        // given
        LocalDateTime a = LocalDateTime.now();
        long piWithoutParallel = ParallelPi.piWithoutParallel(10 ^ 60);
        LocalDateTime b = LocalDateTime.now();
        Duration duration1 = Duration.between(a, b);

        LocalDateTime c = LocalDateTime.now();
        long piParallel = ParallelPi.piWithParallel(10 ^ 60);
        LocalDateTime d = LocalDateTime.now();
        Duration duration2 = Duration.between(c, d);

        // then
        assertThat(duration1.toMillis()).isGreaterThanOrEqualTo(duration2.toMillis());
    }
}