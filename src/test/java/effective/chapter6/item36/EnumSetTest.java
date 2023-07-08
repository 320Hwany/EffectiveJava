package effective.chapter6.item36;

import effective.chapter6.item36.TextGood.Style;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class EnumSetTest {

    @Test
    @DisplayName("비트 필드 대신 EnumSet을 사용하라")
    void test() {
        TextBad textBad = new TextBad();
        int styles1 = textBad.applyStyles(TextBad.STYLE_BOLD | TextBad.STYLE_ITALIC);
        assertThat(styles1).isEqualTo(3); // 단순한 정수 열거 상수 출력

        TextGood textGood = new TextGood();
        // Collections.unmodifiableSet을 이용해서 불변을 보장할 수 있다
        Set<Style> styles2 = Collections.unmodifiableSet(textGood.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC)));
        assertThat(styles2).isEqualTo(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
