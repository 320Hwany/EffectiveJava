package effective.chapter5.item31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Chooser<T> {

    private final List<T> choiceArray;

    public Chooser(Collection<? extends T> choices) {
        this.choiceArray = new ArrayList<>(choices);
    }
}
