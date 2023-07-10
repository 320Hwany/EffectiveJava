package effective.chapter6.item39;

import java.util.ArrayList;
import java.util.List;

// 반복 가능 애너테이션을 두번 단 코드
public class Sample4 {

    @ExceptionTest3(IndexOutOfBoundsException.class)
    @ExceptionTest3(NullPointerException.class)
    public static void doubleBad() { // 성공해야 한다
        List<String> list = new ArrayList<>();

        // NullPointerException 을 던질 수 있다
        list.addAll(5, null);
    }
}
