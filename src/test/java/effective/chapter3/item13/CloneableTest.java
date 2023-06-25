package effective.chapter3.item13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CloneableTest {

    @Test
    @DisplayName("가변 상태를 참조하지 않는 클래스용 clone 메소드")
    void test1() {
        // given
        Member member = new Member("이름",20, 170);

        // when
        Member clone = member.clone();

        // then
        assertThat(member).isNotEqualTo(clone);
        assertThat(member.getName()).isEqualTo(clone.getName());
        assertThat(member.getAge()).isEqualTo(clone.getAge());
        assertThat(member.getHeight()).isEqualTo(clone.getHeight());
    }

    @Test
    @DisplayName("가변 상태를 참조하는 클래스용 clone 메소드")
    void test2() {
        // given
        Stack stack = new Stack();

        // when
        Stack clone = stack.clone();

        // then
        assertThat(stack).isNotEqualTo(clone);
        assertThat(stack.getElements()).isNotSameAs(clone.getElements());
        assertThat(stack.getSize()).isEqualTo(clone.getSize());
    }

    @Test
    @DisplayName("배열만은 clone 메소드 방식이 가장 깔끔한 합당한 예외입니다 - 깊은 복사")
    void test3() {
        // given
        int[] hello = new int[10];

        // when
        int[] clone = hello.clone();

        // expected
        assertThat(hello).isNotSameAs(clone);
        assertThat(hello).isEqualTo(clone);

        // when
        clone[3] = 10;

        // then
        assertThat(hello).isNotEqualTo(clone);
        assertThat(hello[3]).isEqualTo(0);
        assertThat(clone[3]).isEqualTo(10);
    }
}