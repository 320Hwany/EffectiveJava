package effective.chapter10.item73;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {

    @Test
    @DisplayName("상위 계층에서 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꿔 던질 수 있다")
    void test1() {
        // given
        CustomException customException = new CustomException();

        // when
        assertThatThrownBy(() -> customException.exceptionTranslation1())
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    @DisplayName("저수준 예외가 디버깅에 도움이 된다면 예외 연쇄를 사용하는 게 좋다")
    void test2() {
        // given
        CustomException customException = new CustomException();

        // when
        HigherLevelException higherLevelException = customException.exceptionTranslation2();

        // then
        Assertions.assertThat(higherLevelException.toString())
                .isEqualTo("effective.chapter10.item73.HigherLevelException: " +
                        "java.util.NoSuchElementException");
    }
}