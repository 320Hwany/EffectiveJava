package effective.chapter8.item54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StockTest {

    @Test
    @DisplayName("컬렉션이 비었으면 null을 반환한다 - 따라 하지 말것")
    void test1() {
        // given
        Stock stock = new Stock();

        // when
        List<String> stringsBad = stock.getStringsBad();

        // then
        assertThat(stringsBad).isNull();
    }

    @Test
    @DisplayName("빈 컬렉션을 반환하는 올바른 예")
    void test2() {
        // given
        Stock stock = new Stock();

        // when
        List<String> stringsBetter = stock.getStringsBetter();

        // then
        assertThat(stringsBetter).isEmpty();
    }

    @Test
    @DisplayName("최적화 - 빈 컬렉션을 매번 새로 할당하지 않음")
    void test3() {
        // given
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        // when
        List<String> stringOptimization1 = stock1.getStringOptimization();
        List<String> stringOptimization2 = stock2.getStringOptimization();

        // then
        assertThat(stringOptimization1).isEmpty();
        assertThat(stringOptimization2).isEmpty();
        assertThat(stringOptimization1).isEqualTo(stringOptimization2);
    }

    @Test
    @DisplayName("길이가 0일 수도 있는 배열을 반환하는 올바른 방법")
    void test4() {
        // given
        Stock stock = new Stock();

        // when
        String[] stringArray = stock.getStringArray();

        // then
        assertThat(stringArray).isEmpty();
    }

    @Test
    @DisplayName("최적화 - 빈 배열을 매번 새로 할당하지 않도록 하는 방법(항상 최선은 아니다)")
    void test5() {
        // given
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        // when
        String[] stringArrayOptimization1 = stock1.getStringArrayOptimization();
        String[] stringArrayOptimization2 = stock2.getStringArrayOptimization();

        // then
        assertThat(stringArrayOptimization1).isEmpty();
        assertThat(stringArrayOptimization2).isEmpty();
        assertThat(stringArrayOptimization1).isEqualTo(stringArrayOptimization2);
    }
}