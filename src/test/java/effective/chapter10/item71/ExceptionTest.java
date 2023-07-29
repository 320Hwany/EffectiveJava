package effective.chapter10.item71;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class ExceptionTest {

    @Test
    @DisplayName("검사 예외를 던지는 메소드 - 리팩토링 전")
    void test1() {
        // given
        Member member = new Member("이름", 20, false);
        boolean dealWith = false;

        // when
        try {
            member.win1();
        } catch (IOException e) {
            dealWith = true;
        }

        // then
        assertThat(dealWith).isTrue();
    }

    @Test
    @DisplayName("상태 검사 메소드와 비검사 예외를 던지는 메소드 - 리팩토링 후")
    void test2() {
        // given
        Member member = new Member("이름", 20, false);

        // when
        if (member.check()) {
            member.win2();
        } else {
            assertThatThrownBy(() -> member.win2())
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
