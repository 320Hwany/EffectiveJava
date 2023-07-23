package effective.chapter9.item60;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class CalculationTest {

    @Test
    @DisplayName("금융 계산에 부동 소수 타입을 사용하면 오류가 발생할 수 있다")
    void test1() {
        // given
        double funds = 1.00;
        int itemsBought = 0;

        // when
        for (double price = 0.10; funds >= price; price += 0.10) {
            funds -= price;
            itemsBought++;
        }

        // then
        assertThat(itemsBought).isEqualTo(3);
        assertThat(funds).isNotEqualTo(4);
    }

    @Test
    @DisplayName("BigDecimal을 사용한 햅법 - 속도가 느리고 쓰기 불편하다")
    void test2() {
        // given
        BigDecimal TEN_CENTS = new BigDecimal("0.10");
        int itemsBought = 0;
        BigDecimal funds = new BigDecimal("1.00");

        // when
        for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
            funds = funds.subtract(price);
            itemsBought++;
        }

        // then
        assertThat(itemsBought).isEqualTo(4);
        assertThat(funds.doubleValue()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("정수타입을 사용한 해법")
    void test3() {
        // given
        int itemsBought = 0;
        int funds = 100;

        // when
        for (int price = 10; funds >= price; price += 10) {
            funds -= price;
            itemsBought++;
        }

        // then
        assertThat(itemsBought).isEqualTo(4);
        assertThat(funds).isEqualTo(0);
    }
}
