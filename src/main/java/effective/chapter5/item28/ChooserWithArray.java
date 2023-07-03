package effective.chapter5.item28;

import java.util.Collection;

public class ChooserWithArray<T> {

    private final T[] choiceArray;

    public ChooserWithArray(Collection<T> choices) {
        this.choiceArray = (T[]) choices.toArray();
    }
}
