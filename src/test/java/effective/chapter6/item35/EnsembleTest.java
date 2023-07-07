package effective.chapter6.item35;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EnsembleTest {

    @Test
    @DisplayName("ordinal을 잘못 사용한 예, 동작은 하지만 유지보수가 끔찍하다 - bad")
    void test1() {
        // given
        int duet = EnsembleBad.DUET.numberOfMusicians();
        int trio = EnsembleBad.TRIO.numberOfMusicians();

        // then
        assertThat(duet).isEqualTo(2);
        assertThat(trio).isEqualTo(3);
    }

    @Test
    @DisplayName("ordinal 메소드를 사용하지 말고 인스턴스 필드에 저장 - good")
    void test2() {
        // given
        int duet = EnsembleGood.DUET.numberOfMusicians();
        int trio = EnsembleGood.TRIO.numberOfMusicians();

        // then
        assertThat(duet).isEqualTo(2);
        assertThat(trio).isEqualTo(3);
    }
}