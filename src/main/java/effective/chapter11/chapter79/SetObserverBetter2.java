package effective.chapter11.chapter79;

@FunctionalInterface public interface SetObserverBetter2<E> {

    // ObservableSetBetter2 원소가 더해지면 호출된다.
    void added(ObservableSetBetter2<E> set, E element);
}
