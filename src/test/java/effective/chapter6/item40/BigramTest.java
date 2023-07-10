package effective.chapter6.item40;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BigramTest {

    @Test
    @DisplayName("Overriding이 아니라 Overloading을 해도 @Override를 사용하지 않으면 컴파일 에러가 발생하지 않는다")
    void test1() {
        // given
        Set<BigramBad> s = new HashSet<>();

        // when
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new BigramBad(ch, ch));
            }
        }

        // then
        assertThat(s.size()).isEqualTo(260);
    }

    @Test
    @DisplayName("@Override를 사용하면 Overriding을 하지 않았을 때 컴파일 에러를 발생시킬 수 있습니다")
    void test2() {
        // given
        Set<BigramGood> s = new HashSet<>();

        // when
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new BigramGood(ch, ch));
            }
        }

        // then
        assertThat(s.size()).isEqualTo(26);
    }
}
