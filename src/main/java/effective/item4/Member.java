package effective.item4;

public class Member {

    private Member() {
        throw new IllegalArgumentException("인스턴스화 할 수 없습니다");
    }

    public static final String name = "이름";

    public static final int age = 20;
}
