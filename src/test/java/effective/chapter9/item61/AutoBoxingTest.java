package effective.chapter9.item61;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class AutoBoxingTest {

    @Test
    @DisplayName("박싱된 기본 타입에 == 연산자를 사용하면 오류가 발생한다")
    void test1() {
        // given
        Integer a1 = 127;
        Integer a2 = 127;

        Integer b1 = 128;
        Integer b2 = 128;

        // expected
        assertThat(a1).isSameAs(a2); // -128 ~ 127까지는 Integer 캐시 덕분에 == 비교 가능
        assertThat(b1).isNotSameAs(b2);
    }

    @Test
    @DisplayName("기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다")
    void test2() {
        // expected
        Assertions.assertThatThrownBy(AutoUnboxing::unbelievable)
                .isInstanceOf(NullPointerException.class);
    }
}
