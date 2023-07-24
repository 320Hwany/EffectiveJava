package effective.chapter9.item61;

// 기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다
public class Unboxing {

    static Integer i;

    public static void unbelievable() {
        if (i == 42) {
            System.out.println("Unbelievable");
        }
    }
}
