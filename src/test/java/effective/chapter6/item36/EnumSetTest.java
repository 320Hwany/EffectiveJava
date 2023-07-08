package effective.chapter6.item36;

import effective.chapter6.item36.TextGood.Style;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.*;

public class EnumSetTest {

    @Test
    @DisplayName("비트 필드 대신 EnumSet을 사용하라")
    void test() {
        TextBad textBad = new TextBad();
        int styles1 = textBad.applyStyles(TextBad.STYLE_BOLD | TextBad.STYLE_ITALIC);
        assertThat(styles1).isEqualTo(3);

        TextGood textGood = new TextGood();
        int styles2 = textGood.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
        assertThat(styles2).isEqualTo(3);
    }
}
