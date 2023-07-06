package effective.chapter5.item32;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class UseVarargsMethodTest {

    @Test
    @DisplayName("가변인수를 사용하여 메소드에 넘기는 인수의 개수를 클라이언트가 조절할 수 있습니다")
    void test1() {
        // given
        UseVarargsMethod useVarargsMethod = new UseVarargsMethod();
        String[] hello = new String[3];
        hello[0] = "hello1";
        hello[1] = "hello2";
        hello[2] = "hello3";

        String[] world = new String[3];
        world[0] = "world1";
        world[1] = "world2";
        world[2] = "world3";

        // when
        useVarargsMethod.varargsMethod(hello, world);

        // then
        assertThat(useVarargsMethod.getCount()).isEqualTo(6);
    }

    @Test
    @DisplayName("제네릭 varargs 배열 매개변수에 값을 저장하는 것은 타입 안전성이 깨집니다")
    void test2() {
        // given
        UseVarargsMethod useVarargsMethod = new UseVarargsMethod();
        List<String> hello = List.of("hello1", "hello2", "hello3");
        List<String> world = List.of("world1", "world2", "world3");

        // expected
        assertThatThrownBy(() -> useVarargsMethod.varargsMethodWithGeneric(hello, world))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("자신의 제네릭 매개변수 배열의 참조를 노출한다 - 안전하지 않다")
    void test3() {
        // given
        UseVarargsMethod useVarargsMethod = new UseVarargsMethod();

        // expected
        assertThatThrownBy(() -> {
            String[] strings = useVarargsMethod.pickTwo1("hello", "hi", "world");
        })
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("varargs를 사용하지 않고 List.of 를 활용해 컴파일러가 메소드의 타입 안전성을 검증합니다")
    void test4() {
        // given
        UseVarargsMethod useVarargsMethod = new UseVarargsMethod();

        // when
        List<String> strings = useVarargsMethod.pickTwo2("hello", "hi", "world");

        // then
        assertThat(strings.size()).isEqualTo(2);
    }
}