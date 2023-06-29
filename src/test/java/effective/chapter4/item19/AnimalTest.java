package effective.chapter4.item19;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AnimalTest {

    @Test
    @DisplayName("상속용 클래스의 생성자에 재정의 가능 메소드가 있어 final로 정의된 필드의 상태가 2가지입니다")
    void test() {
        // given
        Tiger tiger = new Tiger();  // 이때는 name이 null이다.

        // expected
        assertThat(tiger.getName()).isEqualTo("tiger");
    }
}