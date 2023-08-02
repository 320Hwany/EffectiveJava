package effective.chapter11.item79;

@FunctionalInterface public interface SetObserverBetter1<E> {

    // ObservableSetBetter1 원소가 더해지면 호출된다.
    void added(ObservableSetBetter1<E> set, E element);
}
