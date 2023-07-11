package effective.chapter7.item42;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class LambdaTest {

    @Test
    @DisplayName("익명 클래스의 인스턴스를 함수 객체로 사용 - 낡은 기법")
    void test1() {
        // given
        List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

        // when
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // then
        assertThat(words.get(0)).isEqualTo("hi");
        assertThat(words.get(1)).isEqualTo("hello");
        assertThat(words.get(2)).isEqualTo("world");
    }

    @Test
    @DisplayName("람다식을 함수 객체로 사용 - 익명 클래스 대체")
    void test2() {
        // given
        List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

        // when
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // then
        assertThat(words.get(0)).isEqualTo("hi");
        assertThat(words.get(1)).isEqualTo("hello");
        assertThat(words.get(2)).isEqualTo("world");
    }

    @Test
    @DisplayName("람다자리에 비교자 생성 메소드를 사용하여 더 간결하게 표현")
    void test3() {
        // given
        List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

        // when
        Collections.sort(words, Comparator.comparingInt(String::length));

        // then
        assertThat(words.get(0)).isEqualTo("hi");
        assertThat(words.get(1)).isEqualTo("hello");
        assertThat(words.get(2)).isEqualTo("world");
    }

    @Test
    @DisplayName("자바 8때 List 인터페이스에 추가된 sort 메소드를 이용해 더 간결하게 표현")
    void test4() {
        // given
        List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

        // when
        words.sort(Comparator.comparingInt(String::length));

        // then
        assertThat(words.get(0)).isEqualTo("hi");
        assertThat(words.get(1)).isEqualTo("hello");
        assertThat(words.get(2)).isEqualTo("world");
    }

    @Test
    @DisplayName("열거 타입에 인스턴스 필드를 두는 방식")
    void test5() {
        // when
        double plus = Operation.PLUS.apply(10, 20);
        double minus = Operation.MINUS.apply(10, 20);
        double times = Operation.TIMES.apply(10, 20);
        double divide = Operation.DIVIDE.apply(10, 20);

        // then
        assertThat(plus).isEqualTo(30);
        assertThat(minus).isEqualTo(-10);
        assertThat(times).isEqualTo(200);
        assertThat(divide).isEqualTo(0.5);
    }
}
