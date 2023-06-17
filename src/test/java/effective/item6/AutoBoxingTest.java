package effective.item6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class AutoBoxingTest {

    @Test
    @DisplayName("오토 박싱은 성능상으로 문제가 될 수 있습니다")
    void test() {
        // given
        AutoBoxing autoBoxing = new AutoBoxing();

        // when
        LocalDateTime a = LocalDateTime.now();
        Long sumBayWay = autoBoxing.sumBadWay();
        LocalDateTime b = LocalDateTime.now();

        Duration duration1 = Duration.between(a, b);
        System.out.println(duration1.toMillis()); // 출력 결과 : 5639

        LocalDateTime c = LocalDateTime.now();
        long sumGoodWay = autoBoxing.sumGoodWay();
        LocalDateTime d = LocalDateTime.now();

        Duration duration2 = Duration.between(c, d);
        System.out.println(duration2.toMillis());  // 출력 결과 : 784
    }
}
