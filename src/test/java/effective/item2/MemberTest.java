package effective.item2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("일반적인 생성자는 매개변수가 많으면 가독성이 떨어지며 상황에 따라 생성자를 여러 개 만들어야 합니다")
    void test1() {
        // given
        Member member = new Member("이름", 20, "email", 170, 60);

        // expected
        assertThat(member.getName()).isEqualTo("이름");
        assertThat(member.getAge()).isEqualTo(20);
        assertThat(member.getEmail()).isEqualTo("email");
        assertThat(member.getHeight()).isEqualTo(170);
        assertThat(member.getWeight()).isEqualTo(60);
    }

    @Test
    @DisplayName("자바빈즈패턴은 클래스를 불변으로 만들 수 없으며 여러 개의 메소드를 호출해야합니다")
    void test2() {
        // given
        Member member = new Member();

        // when
        member.setName("이름");
        member.setAge(20);
        member.setEmail("email");
        member.setHeight(170);
        member.setWeight(60);

        // then
        assertThat(member.getName()).isEqualTo("이름");
        assertThat(member.getAge()).isEqualTo(20);
        assertThat(member.getEmail()).isEqualTo("email");
        assertThat(member.getHeight()).isEqualTo(170);
        assertThat(member.getWeight()).isEqualTo(60);
    }
}