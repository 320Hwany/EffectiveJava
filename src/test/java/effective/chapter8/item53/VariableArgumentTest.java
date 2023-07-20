package effective.chapter8.item53;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VariableArgumentTest {

    @Test
    @DisplayName("가변인수를 이용한 합 구하기")
    void test1() {
        // given
        int sum1 = VariableArgument.sum(1, 2, 3);
        int sum2 = VariableArgument.sum(1, 2, 3, 4, 5);

        // expected
        assertThat(sum1).isEqualTo(6);
        assertThat(sum2).isEqualTo(15);
    }

    @Test
    @DisplayName("가변인수 메소드에 인수가 1개이상이 아니라면 런타임 시점에 예외가 발생합니다 - 잘못 구현한 예")
    void test2() {
        // expected
        assertThatThrownBy(VariableArgument::minBad)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("가변인수 메소드에 인수가 1개이상이 아니라면 컴파일 시점에 에러가 발생해 아예 실행할 수 없습니다 - 제대로 구현")
    void test3() {
        // given
        int min = VariableArgument.minGood(1, 2, 3);

        // expected
        assertThat(min).isEqualTo(1);
    }
}