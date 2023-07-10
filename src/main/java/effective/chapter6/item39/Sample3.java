package effective.chapter6.item39;

import java.util.ArrayList;
import java.util.List;

// 배열 매개변수를 받는 애너테이션 타입
public class Sample3 {

    @ExceptionTest2({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void doubleBad() { // 성공해야 한다
        List<String> list = new ArrayList<>();

        // NullPointerException 을 던질 수 있다
        list.addAll(5, null);
    }
}
