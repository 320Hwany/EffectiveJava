package effective.chapter10.item75;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionTest {

    @Test
    @DisplayName("예외의 상세 메세지에 실패 관련 정보를 담아야 한다")
    void test1() {
        // given
        CustomIndexOutOfBoundsException exception = new CustomIndexOutOfBoundsException(10, 20, 0);

        // when
        String detailMessage = exception.getDetailMessage();

        // then
        assertThat(detailMessage).isEqualTo("최솟값: 10, 최댓값: 20, 인덱스: 0");
    }
}
