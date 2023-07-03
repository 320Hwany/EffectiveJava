package effective.chapter5.item28;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListTest {

    @Test
    @DisplayName("배열은 공변이어서 런타임시에 오류가 발생하고 리스트는 불공변이어서 컴파일시에 오류가 발생합니다")
    void test1() {
        // given
        Object[] objectArray = new Long[1];

        // expected
        assertThatThrownBy(() -> objectArray[0] = "타입이 달라 런타임시에 오류가 발생합니다")
                .isInstanceOf(ArrayStoreException.class);

        // 리스트는 불공변이어서 컴파일시에 오류가 발생합니다
        // List<Object> ol = new ArrayList<Long>(); // 호환되지 않는 타입이다

        // 제네릭 배열은 만들지 못합니다
        // List<String>[] strings = new List<String>[1];
    }
}
