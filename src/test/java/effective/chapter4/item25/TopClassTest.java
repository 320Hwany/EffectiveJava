package effective.chapter4.item25;

import effective.chapter4.item25.good.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TopClassTest {

    @Test
    @DisplayName("2개의 톱레벨 클래스를 정적 멤버 클래스로 바꾼 방법 - good")
    void test() {
        // given
        Member.Member1 member1 = new Member.Member1();
        Member.Member2 member2 = new Member.Member2();

        // expected
        assertThat(member1.getNAME()).isEqualTo("member1 이름");
        assertThat(member2.getNAME()).isEqualTo("member2 이름");
    }
}
