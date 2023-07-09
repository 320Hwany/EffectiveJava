package effective.chapter6.item38;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class OperationTest {

    @Test
    @DisplayName("Operation 인터페이스를 확장한 BasicOperation 열거 타입")
    void test1() {
        // given
        double x = 10;
        double y = 20;

        // when
        List<Double> result = operation1(BasicOperation.class, x, y);

        // then
        assertThat(result.get(0)).isEqualTo(30);
        assertThat(result.get(1)).isEqualTo(-10);
        assertThat(result.get(2)).isEqualTo(200);
        assertThat(result.get(3)).isEqualTo(0.5);
    }

    @Test
    @DisplayName("Operation 인터페이스를 확장한 ExtendedOperation 열거 타입")
    void test2() {
        // given
        double x = 10;
        double y = 20;

        // when
        List<Double> result = operation2(Arrays.asList(ExtendedOperation.values()), x, y);

        // then
        assertThat(result.get(0)).isEqualTo(1.0E20);
        assertThat(result.get(1)).isEqualTo(10);
    }

    private static <T extends Enum<T> & Operation> List<Double> operation1(Class<T> opEnumType, double x, double y) {
        List<Double> result = new ArrayList<>();
        for (Operation op : opEnumType.getEnumConstants()) {
            result.add(op.apply(x, y));
        }
        return result;
    }

    private static List<Double> operation2(Collection<? extends Operation> opSet, double x, double y) {
        List<Double> result = new ArrayList<>();
        for (Operation op : opSet) {
            result.add(op.apply(x, y));
        }
        return result;
    }
}
