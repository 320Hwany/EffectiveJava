package effective.chapter7.item47;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.*;

public class StreamTest {

    @Test
    @DisplayName("Stream<E>를 Iterable<E>로 중개해주는 어댑터")
    void test1() {
        // given
        List<String> strings = List.of("hello", "hi", "world");
        Stream<String> stream = strings.stream();

        // when
        Iterator<String> iterator = stream.iterator();
        String hello = iterator.next();
        String hi = iterator.next();
        String world = iterator.next();

        // then
        assertThat(hello).isEqualTo("hello");
        assertThat(hi).isEqualTo("hi");
        assertThat(world).isEqualTo("world");
    }

    @Test
    @DisplayName("Iterable<E>를 Stream<E>로 중개해주는 어댑터")
    void test2() {
        // given
        List<Integer> integers = List.of(10, 20, 30);

        // when
        Stream<Integer> integerStream = streamOf(integers);

        // then
        int sum = integerStream.mapToInt(Integer::intValue).sum();
        assertThat(sum).isEqualTo(60);
    }

    @Test
    @DisplayName("입력 리스트의 모든 부분 리스트를 스트림으로 변환한다")
    void test3() {
        // given
        List<String> strings = List.of("hello", "hi", "world");

        // when
        Stream<List<String>> of = SubLists.of(strings);

        // then
        List<List<String>> collect = of.toList();
        assertThat(collect.size()).isEqualTo(7);
    }

    private static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }

    private static <E> Stream<E> streamOf(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
