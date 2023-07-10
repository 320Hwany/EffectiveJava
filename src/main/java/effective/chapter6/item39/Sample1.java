package effective.chapter6.item39;

// 마커 애너테이션을 사용한 프로그램
public class Sample1 {

    @CustomTest
    public static void m1() {}

    public static void m2() {}

    @CustomTest
    public static void m3() {
        throw new RuntimeException("실패");
    }

    public static void m4() {}

    @CustomTest
    public void m5() {}

    public static void m6() {}

    @CustomTest
    public static void m7() {
        throw new RuntimeException("실패");
    }

    public static void m8() {}
}
