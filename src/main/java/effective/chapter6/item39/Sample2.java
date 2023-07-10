package effective.chapter6.item39;

public class Sample2 {

    @ExceptionTest1(ArithmeticException.class)
    public static void m1() { // 성공해야 한다
        int i = 0;
        i = i / i;
    }

    @ExceptionTest1(ArithmeticException.class)
    public static void m2() { // 실패해야 한다 (다른 예외 발생)
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest1(ArithmeticException.class)
    public static void m3() {} // 실패해야 한다 (예외가 발생하지 않음)
}
