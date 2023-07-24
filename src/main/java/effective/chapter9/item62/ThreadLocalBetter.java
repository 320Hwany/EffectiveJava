package effective.chapter9.item62;

public final class ThreadLocalBetter<T> {

    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T value) {
        this.key = value;
    }
}
