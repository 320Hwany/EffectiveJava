package effective.chapter9.item62;

// 스레드마다 지역 변수를 가지고 타입 안전성을 확보한 권한 부여 방법
public final class ThreadLocalBetter<T> {

    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T value) {
        this.key = value;
    }
}
