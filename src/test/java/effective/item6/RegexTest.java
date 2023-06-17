package effective.item6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class RegexTest {

    @Test
    @DisplayName("Pattern 인스턴스를 클래스 초기화 과정에서 직접 생성해 캐싱해두고 이 인스턴스를 재사용합니다")
    void test() {
        // given
        LocalDateTime a = LocalDateTime.now();

        for (int i = 0; i < 10000; i++) {
            Regex.isBadWay("hello world");
        }
        LocalDateTime b = LocalDateTime.now();

        Duration duration1 = Duration.between(a, b);
        System.out.println(duration1.toMillis()); // 출력 결과 : 33

        // when
        LocalDateTime c = LocalDateTime.now();
        for (int i = 0; i < 10000; i++) {
            Regex.isGoodWay("hello world");
        }
        LocalDateTime d = LocalDateTime.now();

        Duration duration2 = Duration.between(c, d);
        System.out.println(duration2.toMillis()); // 출력 결과 : 2
    }
}