package effective.chapter4.item17;

public class Calculator {

    private final int a;

    private final int b;

    private Calculator(final int a, final int b) {
        this.a = a;
        this.b = b;
    }

    public static Calculator valueOf(final int a, final int b) {
        return new Calculator(a, b);
    }

    public int plus() {
        return a + b;
    }

    public int minus() {
        return a - b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
