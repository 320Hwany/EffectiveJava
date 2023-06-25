package effective.chapter3.item11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("equals 비교에 사용되는 정보가 변경되지 않았다면 애플리케이션이 실행되는 동안 그 객체의 hashCode 메소드는 " +
            "몇 번을 호출해도 일관되게 항상 같은 값을 반환해야 합니다")
    void test1() {
        // given
        Member member = new Member("이름", 20, 170);

        // when
        int hashCode1 = member.hashCode();
        int hashCode2 = member.hashCode();

        // then
        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    @DisplayName("equals가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 합니다")
    void test2() {
        // given
        Member member1 = new Member("이름", 20, 170);
        Member member2 = new Member("이름", 20, 170);

        // when
        int hashCode1 = member1.hashCode();
        int hashCode2 = member2.hashCode();

        // then
        assertThat(member1).isEqualTo(member2);
        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    @DisplayName("equals가 두 객체를 다르다고 판단했더라도 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없지만 " +
            "다른 객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아집니다")
    void test3() {
        // given
        Member member1 = new Member("이름1", 21, 171);
        Member member2 = new Member("이름2", 22, 172);

        // when
        int hashCode1 = member1.hashCode();
        int hashCode2 = member2.hashCode();

        // then
        assertThat(member1).isNotEqualTo(member2);
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }
}