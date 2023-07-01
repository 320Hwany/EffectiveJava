package effective.chapter4.item22;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloConstantTest {

    @Test
    @DisplayName("상수 인터페이스 - 안티 패턴이니 사용 금지")
    void test1() {
        // given
        String hello = HelloInterface.hello;
        String world = HelloInterface.world;

        // expected
        assertThat(hello).isEqualTo("hello");
        assertThat(world).isEqualTo("world");
    }

    @Test
    @DisplayName("private 생성자로 인스턴스화를 방지한 상수 클래스 - 여전히 리플렉션으로는 인스턴스 가능하다")
    void test2() {
        // given
        String hello = HelloConstant.hello;
        String world = HelloConstant.world;

        // expected
        assertThat(hello).isEqualTo("hello");
        assertThat(world).isEqualTo("world");
    }

    @Test
    @DisplayName("enum 클래스를 활용한 상수 관리 - 완벽한 싱글톤을 보장한다")
    void test3() {
        // given
        String hello = HelloConstantSingleton.HELLO.getValue();
        String world = HelloConstantSingleton.WORLD.getValue();

        // expected
        assertThat(hello).isEqualTo("hello");
        assertThat(world).isEqualTo("world");
    }
}