package effective.chapter6.item36;

import java.util.Set;

// EnumSet - 비트 필드를 대체하는 현대적인 기법
public class TextGood {

    public enum Style {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKETHROUGH;
    }

    // 어떤 Set을 넘겨도 되나, EnumSet이 가장 좋다
    public Set<Style> applyStyles(Set<Style> styles) {
        System.out.println(styles);
        return styles;
    }
}
