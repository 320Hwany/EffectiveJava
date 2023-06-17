package effective.item4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("모든 생성자는 명시적이든 묵시적이든 상위 클래스의 생성자를 호출합니다")
    void test1() {
        MemberChild memberChild = new MemberChild();
    }
}