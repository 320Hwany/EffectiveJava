package effective.chapter5;

import effective.chapter5.item26.Lion;
import effective.chapter5.item26.Tiger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class RawTypeTest {

    @Test
    @DisplayName("로 타입은 타입을 잘못 넣어도 컴파일 시점에 잡을 수 없습니다")
    void test1() {
        // given
        List tigers = new ArrayList();
        tigers.add(new Tiger("호랑이 이름"));
        tigers.add(new Lion("사자 이름")); // 실수로 사자를 넣어도 컴파일 타임에 모른다

        // when
        Tiger tiger1 = (Tiger) tigers.get(0);
        assertThatThrownBy(() -> {
            Tiger tiger2 = (Tiger) tigers.get(1);
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("비한정적 와일드카드 타입은 안전하고 로 타입은 안전하지 않다")
    void test2() {
        // given
        Set<Integer> s1 = Set.of(1, 2, 3);
        Set<Integer> s2 = Set.of(1, 2, 3, 4, 5);
        Set s3 = Set.of(1, 2, 3, "4", "5"); // 타입이 안맞아도 컴파일 단계에서 잡아주지 못한다

        // when
        int result = numElementsInCommon(s1, s2);

        // then
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("로 타입을 사용해도 되는 예외 1 - class 리터럴")
    void test3() {
        // 가능
        Class<List> listClass = List.class;
        Class<String[]> aClass = String[].class;
        Class<Integer> integerClass = int.class;

        // 불가능
        // List<String>.class
        // List<?>.class
    }

    @Test
    @DisplayName("로 타입을 사용해도 되는 예외 2 - instanceOf 연산자")
    void test4() {
        // given
        List<String> strings = new ArrayList<>();
        boolean b1 = false;
        boolean b2 = false;

        // when
        if(strings instanceof List){
            b1 = true;
        }

        if (strings instanceof Set) {
            b2 = true;
        }

        // then
        assertThat(b1).isTrue();
        assertThat(b2).isFalse();
    }

    static int numElementsInCommon(Set<?> s1, Set<?> s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }
}
