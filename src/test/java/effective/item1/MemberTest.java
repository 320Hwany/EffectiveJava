package effective.item1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("호출될 때마다 인스턴스를 새로 생성하지 않습니다")
    void memberSingleton() {
        // given
        MemberSingleton memberSingleton1 = MemberSingleton.getInstance();
        MemberSingleton memberSingleton2 = MemberSingleton.getInstance();

        // expected
        assertThat(memberSingleton1).isEqualTo(memberSingleton2);
    }

    @Test
    @DisplayName("정적 팩토리 메소드를 사용하여 이름을 가질 수 있으며 입력 매개변수에 따라 매번 다른 클래스를 반환합니다")
    void canNamingAndReturnDifferentObject() {
        // given
        Member member1 = Member.toMember("이름1", 10);
        Member member2 = Member.toMember("이름2", 20);

        // expected
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    @DisplayName("Boolean.valueOf() 메소드는 객체를 생성하지 않습니다")
    void notMakeObjectBooleanValueOf() {
        // given
        boolean hello1 = true;
        boolean hello2 = true;

        Boolean hello1Boolean = Boolean.valueOf(hello1);
        Boolean hello2Boolean = Boolean.valueOf(hello2);

        // expected
        assertThat(hello1Boolean).isTrue();
        assertThat(hello2Boolean).isTrue();
    }
}
