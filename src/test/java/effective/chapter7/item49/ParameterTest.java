package effective.chapter7.item49;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParameterTest {

    @Test
    @DisplayName("자바의 null 검사 기능 사용하기")
    void test1() {
        // expected
        assertThatThrownBy(() -> new Member(null, 20))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("이름을 입력해주세요");
    }

    @Test
    @DisplayName("assert 조건에 맞지 않는 매개변수가 들어오면 AssertionError가 발생합니다")
    void test2() {
        // given
        Member member = new Member("name", 20);

        // expected
        assertThatThrownBy(() -> member.update(null, 24))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    @DisplayName("assert 조건에 맞는 매개변수가 들어오면 메소드가 정상적으로 실행됩니다")
    void test3() {
        // given
        Member member = new Member("name", 20);

        // when
        member.update("update name", 24);

        // then
        assertThat(member.getName()).isEqualTo("update name");
        assertThat(member.getAge()).isEqualTo(24);
    }
}
