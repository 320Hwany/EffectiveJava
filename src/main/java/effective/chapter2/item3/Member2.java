package effective.chapter2.item3;

public class Member2 {

    private static final Member2 INSTANCE = new Member2();

    private Member2() {
    }


    public static Member2 getInstance() {
        return INSTANCE;
    }
}
