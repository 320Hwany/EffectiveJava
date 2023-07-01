package effective.chapter4.item21;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SingerTest {

    @Test
    @DisplayName("인터페이스의 default 메소드 추가는 구현 클래스 모두에게 영향을 미칩니다")
    void test() {
        // given
        Singer singer1 = new Singer1();
        Singer singer2 = new Singer2();

        // when
        String sing1 = singer1.sing();
        String dance1 = singer1.dance();
        String hello1 = singer1.hello(); // default 메소드

        String sing2 = singer2.sing();
        String dance2 = singer2.dance();
        String hello2 = singer2.hello(); // default 메소드

        // then
        assertThat(sing1).isEqualTo("Singer1 sing");
        assertThat(dance1).isEqualTo("Singer1 dance");
        assertThat(hello1).isEqualTo("hello");
        assertThat(sing2).isEqualTo("Singer2 sing");
        assertThat(dance2).isEqualTo("Singer2 dance");
        assertThat(hello2).isEqualTo("hello");
        assertThat(hello1).isEqualTo(hello2);
    }
}