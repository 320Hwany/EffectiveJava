package effective.chapter4.item20;

import effective.chapter4.item20.member.HelloHowAreYou;
import effective.chapter4.item20.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("익명 클래스를 활용한 골격 구현 클래스르 정의합니다 - 템플릿 메소드 패턴")
    void test() {
        // given
        Member member = new Member();

        // when
        member.hi();
        HelloHowAreYou helloHowAreYou = member.helloHowAreYou();
        helloHowAreYou.hello();
        helloHowAreYou.howAreYou();

        // then
        assertThat(member.getHi()).isEqualTo("Hi");
        assertThat(member.getHello()).isEqualTo("Hello");
        assertThat(member.getHowAreYou()).isEqualTo("How are you");
    }
}