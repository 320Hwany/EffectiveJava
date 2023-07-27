package effective.chapter10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class ExceptionTest {

    @Test
    @DisplayName("예외를 완전히 잘못 사용한 예 - 따라 하지 말 것!")
    void test1() {
        // given
        Mountain[] range = new Mountain[]{
            new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain()
        };

        int i = 0;

        // when
        try {
            while (true) {
                range[i++].climb();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        assertThat(i).isEqualTo(6);
    }

    @Test
    @DisplayName("test1을 예외를 사용하지 않고 올바르게 수정한 예시")
    void test2() {
        // given
        Mountain[] range = new Mountain[]{
                new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain()
        };

        int i = 0;

        // when
        for (Mountain m : range) {
            i++;
            m.climb();
        }

        // then
        assertThat(i).isEqualTo(5);
    }

    @Test
    @DisplayName("상태 의존적 메소드를 상태 검사 메소드와 함께 제공")
    void test3() {
        // given
        List<Mountain> mountains =
                List.of(new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain());
        int k = 0;

        // when
        for (Iterator<Mountain> i = mountains.iterator(); i.hasNext(); ) {
            k++;
            Mountain mountain = i.next();
            mountain.climb();
        }

        // then
        assertThat(k).isEqualTo(5);
    }

    @Test
    @DisplayName("상태 검사 메소드를 사용하지 않는다면 클라이언트가 직접 해야함 - 하지 말 것")
    void test4() {
        // given
        List<Mountain> mountains =
                List.of(new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain());
        int k = 0;

        try {
            Iterator<Mountain> i = mountains.iterator();
            while (true) {
                k++;
                Mountain mountain = i.next();
                mountain.climb();
            }
        } catch (NoSuchElementException e) {
        }

        // then
        assertThat(k).isEqualTo(6);
    }
}
