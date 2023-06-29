package effective.chapter4.item18;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class InstrumentedHashSetTest {

    @Test
    @DisplayName("addAll() 메소드 실행 후 3을 반환하리라 기대하지만 6을 반환합니다 - HashSet 상속")
    void test() {
        // given
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();

        // when
        s.addAll(List.of("1", "2", "3"));

        // 3이 아니라 6을 반환한다
        assertThat(s.getAddCount()).isEqualTo(6);
    }
}