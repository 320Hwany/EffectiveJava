package effective.chapter10.item72;

import java.util.ConcurrentModificationException;

// 표준 예외를 사용하는 클래스
public class CustomException {

    public void makeIllegalArgumentException() {
        // 허용되지 않는 값이 인수로 건네졌을 때
        throw new IllegalArgumentException();
    }

    public void makeIllegalStateException() {
        // 객체가 메소드를 수행하기에 적절하지 않은 상태일 때
        throw new IllegalStateException();
    }

    public void makeNullPointerException() {
        // null을 허용하지 않는 메소드에 null을 건넸을 때
        throw new NullPointerException();
    }

    public void makeIndexOutOfBoundsException() {
        // 인덱스가 범위를 넘어섰을 때
        throw new IndexOutOfBoundsException();
    }

    public void makeConcurrentModificationException() {
        // 허용하지 않는 동시 수정이 발견됐을 때
        throw new ConcurrentModificationException();
    }

    public void makeUnsupportedOperationException() {
        // 호출한 메소드를 지원하지 않을 때
        throw new UnsupportedOperationException();
    }
}
