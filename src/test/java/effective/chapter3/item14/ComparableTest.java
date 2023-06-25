package effective.chapter3.item14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparableTest {

    @Test
    @DisplayName("HashSet은 equals 메소드로 시교하여 1.0과 1.00은 서로 다릅니다")
    void test1() {
        // given
        BigDecimal bigDecimal1 = new BigDecimal("1.0");
        BigDecimal bigDecimal2 = new BigDecimal("1.00");

        Set<BigDecimal> decimals = new HashSet<>();
        decimals.add(bigDecimal1);
        decimals.add(bigDecimal2);

        // expected
        assertThat(decimals.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("TreeSet은 compareTo 메소드로 시교하여 1.0과 1.00은 서로 같습니다")
    void test2() {
        // given
        BigDecimal bigDecimal1 = new BigDecimal("1.0");
        BigDecimal bigDecimal2 = new BigDecimal("1.00");

        Set<BigDecimal> decimals = new TreeSet<>();
        decimals.add(bigDecimal1);
        decimals.add(bigDecimal2);

        // expected
        assertThat(decimals.size()).isEqualTo(1);
    }
}
