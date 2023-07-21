package effective.chapter8.item55;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.*;

class OptionalTest {

    @Test
    @DisplayName("옵셔널 활용 1 - 기본 값을 정해둘 수 있다")
    void test1() {
        // given
        List<Integer> integers1 = List.of(10, 20, 30);
        List<Integer> integers2 = List.of();

        // when
        Integer value1 = OptionalUse.maxWithOptional(integers1).orElse(0);
        Integer value2 = OptionalUse.maxWithOptional(integers2).orElse(-1);

        // then
        assertThat(value1).isEqualTo(30);
        assertThat(value2).isEqualTo(-1);
    }

    @Test
    @DisplayName("옵셔널 활용 2 - 원하는 예외를 던질 수 있다")
    void test2() {
        // given
        List<Integer> integers1 = List.of(10, 20, 30);
        List<Integer> integers2 = List.of();

        // when
        Integer value1 = OptionalUse.maxWithOptional(integers1)
                .orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(value1).isEqualTo(30);
        assertThatThrownBy(() -> throwError(integers2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("int, long, double 기본타입 전용 옵셔널 클래스가 존재한다")
    void test3() {
        // given
        OptionalUse optionalUse = new OptionalUse();
        int a = 10;
        long b = 20;
        double c = 30;

        // when
        OptionalInt optionalInt = optionalUse.optionalInt(a);
        OptionalLong optionalLong = optionalUse.optionalLong(b);
        OptionalDouble optionalDouble = optionalUse.optionalDouble(c);

        int asInt = optionalInt.getAsInt();
        long asLong = optionalLong.getAsLong();
        double asDouble = optionalDouble.getAsDouble();

        // then
        assertThat(asInt).isEqualTo(10);
        assertThat(asLong).isEqualTo(20);
        assertThat(asDouble).isEqualTo(30);
    }

    private void throwError(List<Integer> integers) {
        OptionalUse.maxWithOptional(integers)
                .orElseThrow(IllegalArgumentException::new);
    }
}