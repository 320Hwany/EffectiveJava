package effective.item12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("toString을 재정의해서 간결하면서도 사람이 읽기 쉬운 형태의 유익한 정보를 반환합니다")
    void test() {
        // given
        Member member = new Member("회원 이름", 20, 170);

        // toString을 재정의했기 때문에 로그를 남길 때 훨씬 유용한 정보를 남길 수 있습니다
        System.out.println(member);
    }
}