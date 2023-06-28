package effective.chapter4.item17;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class CalculatorTest {

    @Test
    @DisplayName("불변 클래스에서 피연산자 자체는 변하지 않는 함수형 프로그래밍 방식")
    void test1() {
        // given
        Calculator calculator = Calculator.valueOf(30, 10);

        // when
        int plus = calculator.plus();
        int minus = calculator.minus();

        // then
        assertThat(plus).isEqualTo(40);
        assertThat(minus).isEqualTo(20);
        assertThat(calculator.getA()).isEqualTo(30);
        assertThat(calculator.getB()).isEqualTo(10);
    }

    @Test
    @DisplayName("불변 객체는 Map의 키나 Set의 원소로 사용하기에 용이합니다")
    void test2() {
        // given
        Calculator calculator = Calculator.valueOf(30, 10);
        Set<Integer> calculatorSet = new HashSet<>();

        // when
        calculatorSet.add(calculator.getA());
        calculatorSet.add(calculator.getB());

        // then
        assertThat(calculatorSet.size()).isEqualTo(2);
    }
}