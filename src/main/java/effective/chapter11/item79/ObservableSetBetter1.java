package effective.chapter11.item79;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// 외계인 메소드를 동기화 블록 바깥으로 옮겼다
public class ObservableSetBetter1<E> extends ForwardingSet<E> {

    public ObservableSetBetter1(Set<E> set) {
        super(set);
    }

    private final List<SetObserverBetter1<E>> observers = new ArrayList<>();

    public void addObserver(SetObserverBetter1<E> observer) {
        synchronized(observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserverBetter1<E> observer) {
        synchronized(observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        List<SetObserverBetter1<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (SetObserverBetter1<E> observer : snapshot) {
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
