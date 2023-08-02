package effective.chapter11.chapter79;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

// CopyOnWriteArrayList를 사용해 구현한 스레드 안전하고 관찰 가능한 집합
public class ObservableSetBetter2<E> extends ForwardingSet<E> {

    public ObservableSetBetter2(Set<E> set) {
        super(set);
    }

    private final List<SetObserverBetter2<E>> observers = new CopyOnWriteArrayList<>();

    public void addObserver(SetObserverBetter2<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver(SetObserverBetter2<E> observer) {
        return observers.remove(observer);
    }

    private void notifyElementAdded(E element) {
        for (SetObserverBetter2<E> observer : observers) {
            observer.added(this, element);
        }
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element);
        return result;
    }
}