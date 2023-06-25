package effective.chapter4.item15;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DealWithArrayTest {

    @Test
    @DisplayName("public static final 로 선언한 배열은 불변일 것 같지만 수정이 가능하다")
    void test1() {
        // given
        DealWithArray publicArray = new DealWithArray();

        // when
        int[] values = publicArray.valuesBadWay;
        values[2] = 1;

        // then
        assertThat(values[2]).isEqualTo(1);
    }

    @Test
    @DisplayName("배열을 private으로 만들고 public 불변 리스트를 추가하면 수정이 불가능합니다")
    void test2() {
        // given
        DealWithArray publicArray = new DealWithArray();

        // when
        List<Integer> values = publicArray.valuesGoodWay1;

        // expected
        assertThatThrownBy(() -> values.set(2, 1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("배열을 private으로 만들고 복사본을 public 메소드로 반환하면 수정이 불가능합니다")
    void test3() {
        // given
        DealWithArray publicArray = new DealWithArray();

        // when
        int[] values = publicArray.valuesGoodWay2();
    }
}