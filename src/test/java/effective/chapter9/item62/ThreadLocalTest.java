package effective.chapter9.item62;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadLocalTest {

    @Test
    @DisplayName("잘못된 예 - 문자열을 사용해 권한을 부여, 문자열 키가 전역 이름 공간에서 공유됨")
    void test1() {
        // given
        Member member = new Member("이름", 20);
        Map<String, Object> keys = new ConcurrentHashMap<>();

        // when
        ThreadLocalBad.set("hello", member, keys);
        Member getMember = (Member) ThreadLocalBad.get("hello", keys);

        // then
        assertThat(getMember.getName()).isEqualTo("이름");
        assertThat(getMember.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("스레드마다 지역 변수를 가지고 타입 안전성을 확보한 권한 부여 방법")
    void test2() {
        // given
        Member member = new Member("이름", 20);

        // when
        ThreadLocalBetter<Member> keys = new ThreadLocalBetter<>();
        keys.setKey(member);
        Member getMember = keys.getKey();

        // then
        assertThat(getMember.getName()).isEqualTo("이름");
        assertThat(getMember.getAge()).isEqualTo(20);
    }
}
