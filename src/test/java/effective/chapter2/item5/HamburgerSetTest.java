package effective.chapter2.item5;

import effective.chapter2.item5.hamburger.PsyBurger;
import effective.chapter2.item5.side.FrenchFries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HamburgerSetTest {

    @Test
    @DisplayName("의존 관계 주입으로 생성자에 필요한 자원을 넘겨줍니다")
    void getSetInfo() {
        // given
        PsyBurger psyBurger = new PsyBurger();
        FrenchFries frenchFries = new FrenchFries();
        HamburgerSet hamburgerSet = new HamburgerSet(psyBurger, frenchFries);

        // when
        String setName = hamburgerSet.getSetName();
        int setPrice = hamburgerSet.getSetPrice();

        // then
        assertThat(setName).isEqualTo("PsyBurger FrenchFries");
        assertThat(setPrice).isEqualTo(8000);
    }
}