package effective.chapter6.item36;

// 비트 필드 열거 상수 - bad
public class TextBad {

    public static final int STYLE_BOLD = 1 << 0;
    public static final int STYLE_ITALIC = 1 << 1;
    public static final int STYLE_UNDERLINE = 1 << 2;
    public static final int STYLE_STRIKETHROUGH = 1 << 3;

    public int applyStyles(int styles) {
        System.out.println(styles);
        return styles;
    }
}
