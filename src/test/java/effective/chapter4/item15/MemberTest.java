package effective.chapter4.item15;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("")
    void test() {
        // given
        Member member = new Member("이름", 20, 170, 60);

        // when
        String name = member.name;
        int age = member.age;
        float height = member.height;

        // then
    }
}