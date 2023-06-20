package effective.item10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.*;

class EqualsTest {

    @Test
    @DisplayName("Object의 equals 메소드는 기본적으로 오직 자기 자신과만 같습니다(== 비교)")
    void test() {
        // given
        Object o1 = new Object();
        Object o2 = new Object();

        // when
        boolean boolean1 = o1.equals(o1);
        boolean boolean2 = o1.equals(o2);

        // then
        assertThat(boolean1).isTrue();
        assertThat(boolean2).isFalse();
    }

    @Test
    @DisplayName("반사성 : null이 아닌 모든 참조 값 x에 대해, x.equals(x)는 true 입니다.")
    void test2() {
        // given
        Integer a = 10;

        // when
        boolean equalTest = a.equals(a);

        // then
        assertThat(equalTest).isTrue();
    }

    @Test
    @DisplayName("대칭성 : null이 아닌 모든 참조 값 x,y에 대해, x.equals(y)가 true이면 y.equals(x)도 true이다")
    void test3() {
        // given
        Integer a = 10;
        Integer b = 10;

        // when
        boolean equals1 = a.equals(b);
        boolean equals2 = b.equals(a);

        // then
        assertThat(equals1).isTrue();
        assertThat(equals2).isTrue();
    }

    @Test
    @DisplayName("추이성 : null이 아닌 모든 참조 값 x,y,z에 대해, x.equals(y)가 true y.equals(z)도 true이면 " +
            "x.equals(z)도 true이다")
    void test4() {
        // given
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);

        // when
        boolean equals1 = p1.equals(p2);
        boolean equals2 = p2.equals(p3);
        boolean equals3 = p1.equals(p3);

        // then
        assertThat(equals1).isTrue();
        assertThat(equals2).isTrue();
        assertThat(equals3).isFalse(); // 추이성 위배
    }
}