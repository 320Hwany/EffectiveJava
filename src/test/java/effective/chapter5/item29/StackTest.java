package effective.chapter5.item29;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StackTest {

    @Test
    @DisplayName("배열을 사용한 코드를 제네릭으로 만드는 방법1 - 가독성이 더 좋다")
    void test1() {
        // given
        Stack1<String> stack = new Stack1<>();
        String hello = "hello";
        String world = "world";

        // when
        stack.push(world);
        stack.push(hello);

        // then
        assertThat(stack.pop()).isEqualTo("hello");
        assertThat(stack.pop().getClass()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("배열을 사용한 코드를 제네릭으로 만드는 방법2 - 힙 오염을 막을 수 있다")
    void test2() {
        // given
        Stack2<String> stack = new Stack2<>();
        String hello = "hello";
        String world = "world";

        // when
        stack.push(world);
        stack.push(hello);

        // then
        assertThat(stack.pop().toUpperCase()).isEqualTo("HELLO");
        assertThat(stack.pop().toUpperCase()).isEqualTo("WORLD");
    }
}
