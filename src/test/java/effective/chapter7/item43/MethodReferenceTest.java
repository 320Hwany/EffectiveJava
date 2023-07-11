package effective.chapter7.item43;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.DirectoryStream;
import java.security.PrivilegedAction;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;


public class MethodReferenceTest {

    @Test
    @DisplayName("람다와 정적 메소드 참조를 비교")
    void test1() {
        // given
        Predicate<Object> predicate1 = obj -> Objects.isNull(obj);
        Predicate<Object> predicate2 = Objects::isNull;

        // when
        boolean result1 = predicate1.test(null);
        boolean result2 = predicate2.test(null);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    @Test
    @DisplayName("람다와 생성자 메소드 참조를 비교")
    void test2() {
        // given
        Supplier<String> supplier1 = () -> new String();
        Supplier<String> supplier2 = String::new;

        // when
        String result1 = supplier1.get();
        String result2 = supplier2.get();

        // then
        assertThat(result1).isNotNull();
        assertThat(result2).isNotNull();
    }

    @Test
    @DisplayName("람다와 일반 메소드 참조를 비교")
    void test3() {
        // given
        Function<String, Integer> function1 = (str) -> str.length();
        Function<String, Integer> function2 = String::length;

        // when
        Integer helloWorld1 = function1.apply("Hello World");
        Integer helloWorld2 = function2.apply("Hello World");

        // then
        assertThat(helloWorld1).isEqualTo(11);
        assertThat(helloWorld2).isEqualTo(11);
    }
}
