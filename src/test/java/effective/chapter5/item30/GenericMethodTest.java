package effective.chapter5.item30;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.*;

class GenericMethodTest {

    @Test
    @DisplayName("제네릭 메소드를 사용하여 타입 안전성을 높입니다")
    void test1() {
        // given
        Set<String> s1 = Set.of("hello", "world");
        Set<String> s2 = Set.of("java", "programming");

        // when
        Set<String> union = GenericMethod.union(s1, s2);

        // then
        assertThat(union.contains("hello")).isTrue();
        assertThat(union.contains("world")).isTrue();
        assertThat(union.contains("java")).isTrue();
        assertThat(union.contains("programming")).isTrue();
    }

    @Test
    @DisplayName("제네릭 싱글톤을 사용합니다")
    void test2() {
        // given
        String[] strings = {"삼베", "대마", "나일론"};
        UnaryOperator<String> sameString = GenericMethod.identityFunction();

        // when & then
        for (String s : strings) {
            assertThat(s).isEqualTo(sameString.apply(s));
        }

        // given
        Number[] numbers = {1, 2.0, 3L};
        UnaryOperator<Number> sameNumber = GenericMethod.identityFunction();

        // when & then
        for (Number n : numbers) {
            assertThat(n).isEqualTo(sameNumber.apply(n));
        }
    }

    @Test
    @DisplayName("재귀적 타입 한정을 이용해 비교를 하여 사전순으로 가장 늦게오는 문자열을 반환합니다")
    void test3() {
        // given
        List<String> strings = List.of("hello", "how are you", "my name is", "hwany");

        // when
        String max = GenericMethod.max(strings);

        // then
        assertThat(max).isEqualTo("my name is");
    }
}