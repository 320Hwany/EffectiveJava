package effective.chapter6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static effective.chapter6.IntEnumPattern.*;
import static effective.chapter6.PayrollDay.*;
import static org.assertj.core.api.Assertions.*;

public class EnumTest {

    @Test
    @DisplayName("정수 열거 패턴 - 상당히 취약하다")
    void test1() {
        // 사과를 넣어야 하는데 오렌지 관련 정보를 넣어도 컴파일러가 잡지 못한다
        int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;

        // expected
        assertThat(i).isEqualTo(-1);
    }

    @Test
    @DisplayName("지구에서의 무게를 받아 여덟 행성의 무게를 찾을 수 있습니다")
    void test2() {
        // given
        double earthWeight = 185;

        // when
        double mass = earthWeight / Planet.EARTH.getSurfaceGravity();

        // then
        for (Planet p : Planet.values()) {
            assertThat(p.surfaceWeight(mass)).isNotNull();
        }
    }

    @Test
    @DisplayName("상수별 class body와 데이터를 사용한 열거 타입")
    void test3() {
        // given
        double x = 2;
        double y = 4;

        // when
        double plus = Operation.PLUS.apply(x, y);
        double minus = Operation.MINUS.apply(x, y);
        double times = Operation.TIMES.apply(x, y);
        double divide = Operation.DIVIDE.apply(x, y);

        // then
        assertThat(plus).isEqualTo(6);
        assertThat(minus).isEqualTo(-2);
        assertThat(times).isEqualTo(8);
        assertThat(divide).isEqualTo(0.5);
    }

    @Test
    @DisplayName("전략 열거 패턴 타입을 사용해 유연성을 높입니다")
    void test4() {
        // given
        PayType payTypeMonday = MONDAY.getPayType();
        PayType payTypeTuesday = TUESDAY.getPayType();
        PayType payTypeSunday = SUNDAY.getPayType();

        // when
        int mondayPay = payTypeMonday.pay(100, 1);
        int tuesdayPay = payTypeTuesday.pay(100, 1);
        int sundayPay = payTypeSunday.pay(100, 1);

        // then
        assertThat(mondayPay).isEqualTo(tuesdayPay);
        assertThat(mondayPay).isNotEqualTo(sundayPay);
        assertThat(tuesdayPay).isNotEqualTo(sundayPay);
    }
}
