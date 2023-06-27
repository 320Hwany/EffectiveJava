package effective.chapter4.item16;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("필드를 public으로 공개하지 말고 private인 상태에서 public 접근자를 추가한다")
    void test1() {
        // given
        Member member = new Member("회원 이름", 20);

        // when
        String name = member.getName();
        int age = member.getAge();

        // then
        assertThat(name).isEqualTo("회원 이름");
        assertThat(age).isEqualTo(20);
    }

    @Test
    @DisplayName("필드가 불변이더라도 public으로 공개하면 API를 변경하지 않고는 표현방식을 바꿀 수 없는 단점이 있습니다")
    void test2() {
        // given
        MemberFinalField memberFinalField = new MemberFinalField("회원 이름", 20);

        // 불변은 보장된다, 하지만 API를 공개한다
        String name = memberFinalField.name;
        int age = memberFinalField.age;

        // then
        assertThat(name).isEqualTo("회원 이름");
        assertThat(age).isEqualTo(20);
    }
}