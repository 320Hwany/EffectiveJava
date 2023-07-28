package effective.chapter10.item70;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomExceptionTest {

    @Test
    @DisplayName("검사 예외는 catch 하거나 throws 해야합니다")
    void test1() {
        // given
        CustomException customException = new CustomException();
        boolean dealWith = false;

        // when
        try {
            customException.makeCheckedException();
        } catch (IOException e) {
            dealWith = true;
        }

        // then
        assertThat(dealWith).isTrue();
    }

    @Test
    @DisplayName("비검사 예외는 catch 하거나 throws를 강제하지 않습니다 - 프로그래머가 따로 처리해줘야 함")
    void test2() {
        // given
        CustomException customException = new CustomException();

        // expected
        assertThatThrownBy(() -> customException.makeUncheckedException())
                .isInstanceOf(RuntimeException.class);
    }
}