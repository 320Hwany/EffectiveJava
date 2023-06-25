package effective.chapter2.item4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("모든 생성자는 명시적이든 묵시적이든 상위 클래스의 생성자를 호출합니다")
    void test1() {
        MemberChild memberChild = new MemberChild();
    }

    @Test
    @DisplayName("정적 필드만을 담은 클래스를 만들고 인스턴스화가 불가능하게 만듭니다")
    void test2() {
        // given
        String name = Member.name;
        int age = Member.age;

        // expected
        assertThat(name).isEqualTo("이름");
        assertThat(age).isEqualTo(20);
    }
}