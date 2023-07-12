package effective.chapter7.item44;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.function.*;

import static org.assertj.core.api.Assertions.*;

public class StandardInterfaceTest {

    @Test
    @DisplayName("LinkedHashMap의 removeEldestEntry를 재정의해 캐시로 사용")
    void test1() {
        // given
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 3;
            }
        };

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);

        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("c")).isEqualTo(3);
        assertThat(map.get("d")).isEqualTo(4);
        assertThat(map.get("e")).isEqualTo(5);
    }

    @Test
    @DisplayName("EldestEntryRemovalFunction을 사용하여 removeEldestEntry를 재정의해 캐시로 사용")
    void test2() {
        // given
        EldestEntryRemovalFunction<String, Integer> removalFunction = (m, eldest) -> m.size() > 3;

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return removalFunction.remove(this, eldest);
            }
        };

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);

        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("c")).isEqualTo(3);
        assertThat(map.get("d")).isEqualTo(4);
        assertThat(map.get("e")).isEqualTo(5);
    }

    @Test
    @DisplayName("CustomLinkedHashMap는 LinkedHashMap을 상속 받아 함수 객체를 받는 생성자를 제공한 Custom 클래스")
    void test3() {
        // given
        BiPredicate<Map<String, Integer>, Map.Entry<String, Integer>> removalFunction = (m, eldest) -> m.size() > 3;

        // when
        CustomLinkedHashMap<String, Integer> map = CustomLinkedHashMap.createCache(removalFunction);

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);

        // then
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("c")).isEqualTo(3);
        assertThat(map.get("d")).isEqualTo(4);
        assertThat(map.get("e")).isEqualTo(5);
    }

    @Test
    @DisplayName("BiPredicate를 사용하여 removeEldestEntry를 재정의해 캐시로 사용")
    void test4() {
        // given
        BiPredicate<Map<String, Integer>, Map.Entry<String, Integer>> removalFunction = (m, eldest) -> m.size() > 3;

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return removalFunction.test(this, eldest);
            }
        };

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);

        assertThat(map.size()).isEqualTo(3);
        assertThat(map.get("c")).isEqualTo(3);
        assertThat(map.get("d")).isEqualTo(4);
        assertThat(map.get("e")).isEqualTo(5);
    }

    @Test
    @DisplayName("UnaryOperator를 사용하여 반환값과 인수의 타입이 같은 함수를 만든다")
    void test5() {
        // given
        UnaryOperator<String> toLowerCaseOperator = String::toLowerCase;

        // when
        String hello = toLowerCaseOperator.apply("HELLO");

        // then
        assertThat(hello).isEqualTo("hello");
    }

    @Test
    @DisplayName("BinaryOperator 사용하여 반환값과 인수의 타입이 같은 함수를 만든다")
    void test6() {
        // given
        BinaryOperator<BigInteger> binaryOperator = BigInteger::add;

        // when
        BigInteger num1 = new BigInteger("10");
        BigInteger num2 = new BigInteger("20");
        BigInteger result = binaryOperator.apply(num1, num2);

        // then
        assertThat(result).isEqualTo(30);
    }

    @Test
    @DisplayName("Predicate를 사용하여 인수하나를 받아 boolean 값을 반환합니다")
    void test7() {
        // given
        Predicate<Collection<?>> isEmptyPredicate = Collection::isEmpty;
        Collection<String> collection = List.of();

        // when
        boolean result = isEmptyPredicate.test(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Function을 사용하여 인수와 반환 타입이 다른 함수를 만듭니다")
    void test8() {
        // given
        Function<Object[], List<Object>> asListFunction = Arrays::asList;

        Object[] array = {1, 2, 3, 4, 5};

        // when
        List<Object> list = asListFunction.apply(array);

        // then
        assertThat(list.size()).isEqualTo(5);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
        assertThat(list.get(3)).isEqualTo(4);
        assertThat(list.get(4)).isEqualTo(5);
    }

    @Test
    @DisplayName("Supplier를 사용하여 인수를 받지 않고 값을 반환하는 함수를 만듭니다")
    void test9() {
        // given
        Supplier<Instant> nowSupplier = Instant::now;

        // when
        Instant currentInstant = nowSupplier.get();

        // then
        assertThat(currentInstant).isNotNull();
    }

    @Test
    @DisplayName("Consumer를 사용하여 인수를 하나 받고 반환 값이 없는 함수를 만듭니다")
    void test10() {
        // given
        Consumer<String> consumer = System.out::println;

        // when && then
        consumer.accept("hello world");
    }
}
