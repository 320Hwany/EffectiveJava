package effective.chapter9.item57;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalVariableTest {

    @Test
    @DisplayName("while 문으로 사용하면 선언한 i의 유효범위가 넓어집니다 - 이 경우에는 for 문을 사용하자")
    void test1() {
        // given
        Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

        // when
        List<String> hobbies = member.getHobbies();

        // then
        int i = 0;
        while (i < hobbies.size()) {
            assertThat(hobbies.contains(hobbies.get(i))).isTrue();
            i++;
        }
        assertThat(i).isEqualTo(5);
    }

    @Test
    @DisplayName("for 문으로 사용하면 선언한 i가 반복문안에서만 유효합니다")
    void test2() {
        // given
        Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

        // when
        List<String> hobbies = member.getHobbies();

        // then
        for (int i = 0; i < hobbies.size(); i++) {
            assertThat(hobbies.contains(hobbies.get(i))).isTrue();
        }
    }

    @Test
    @DisplayName("컬렉션이나 배열을 순회하는 권장 관용구")
    void test3() {
        // given
        Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

        // when
        List<String> hobbies = member.getHobbies();

        // then
        for (String hobby : hobbies) {
            assertThat(hobbies.contains(hobby)).isTrue();
        }
    }
}
