package effective.chapter5.item31;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class StackTest {

    @Test
    @DisplayName("생산자(producer) 매개변수에 와일드카드 타입 적용")
    void test1() {
        // given
        Stack<Number> numberStack = new Stack<>();
        Integer[] values = {1, 2, 3};
        Iterable<Integer> intVal = List.of(values);

        // when
        numberStack.pushAll(intVal);

        // then
        assertThat(numberStack.getSize()).isEqualTo(3);
    }

    @Test
    @DisplayName("소비자(consumer) 매개변수에 와일드카드 타입 적용")
    void test2() {
        // given
        Stack<Number> numberStack = new Stack<>();
        numberStack.push(1);
        numberStack.push(2);
        numberStack.push(3);

        Collection<Object> objects = new ArrayList<>();

        // when
        numberStack.popAll(objects);

        // then
        assertThat(objects.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("파라미터 2개 모두 생산자로 사용하여 와일드카드 타입을 적용")
    void test3() {
        // given
        Set<Integer> integers = Set.of(1, 3, 5);
        Set<Double> doubles = Set.of(2.0, 4.0, 6.0);

        // when
        Set<Number> numbers = GenericMethod.union(integers, doubles);

        // then
        assertThat(numbers.size()).isEqualTo(6);
    }
}