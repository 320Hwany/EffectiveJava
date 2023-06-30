package effective.chapter4.item20.celebrity;

public interface Singer {

    // private이 아닌 클래스 변수만 사용 가능
    String hello = "name";

    void dance();

    // default 메소드는 문서화를 해야합니다 - Singer는 기본적으로 노래를 부릅니다
    default void sing() {
        System.out.println("sing");
    }

    // private 정적 메소드 선언 가능합니다
    private static void hello1() {
        System.out.println(hello);
    }

    // 정적 메소드 선언 가능합니다
    static void hello2() {
        System.out.println(hello);
    }
}
