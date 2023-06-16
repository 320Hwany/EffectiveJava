package effective.item2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberWithBuilderTest {

    @Test
    @DisplayName("빌더 패턴을 적용하면 안정성과 가독성을 겸비합니다")
    void test1() {
        // given
        MemberWithBuilder memberWithBuilder = new MemberWithBuilder.Builder()
                .name("이름")
                .age(20)
                .email("email")
                .height(170)
                .weight(60)
                .build();

        // expected
        assertThat(memberWithBuilder.getName()).isEqualTo("이름");
        assertThat(memberWithBuilder.getAge()).isEqualTo(20);
        assertThat(memberWithBuilder.getEmail()).isEqualTo("email");
        assertThat(memberWithBuilder.getHeight()).isEqualTo(170);
        assertThat(memberWithBuilder.getWeight()).isEqualTo(60);
    }
}