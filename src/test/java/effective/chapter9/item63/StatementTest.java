package effective.chapter9.item63;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StatementTest {

    @Test
    @DisplayName("문자열 연결 연산자(+)는 느릴 수 있다 StringBuilder를 사용하면 문자열 연결 성능이 크게 개선됨 " +
            "자바 버전이 올라가면서 문자열 연결 연산자를 사용해도 최적화를 해주기도 함")
    void test1() {
        // given
        Statement statement = new Statement();

        // when
        long a1 = System.currentTimeMillis();
        statement.statement1();
        long a2 = System.currentTimeMillis();

        long b1 = System.currentTimeMillis();
        statement.statement2();
        long b2 = System.currentTimeMillis();

        // then
        assertThat(a2 - a1).isLessThanOrEqualTo(b2 - b1);
    }
}