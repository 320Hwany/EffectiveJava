package effective.chapter6.item36;

import java.util.Set;

// EnumSet - 비트 필드를 대체하는 현대적인 기법
public class TextGood {

    public enum Style {
        BOLD(1 << 0),
        ITALIC(1 << 1),
        UNDERLINE(1 << 2),
        STRIKETHROUGH(1 << 3);

        private final int value;

        Style(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public int applyStyles(Set<Style> styles) {
        int combinedStyle = 0;
        for (Style style : styles) {
            combinedStyle |= style.getValue();
        }
        return combinedStyle;
    }
}
