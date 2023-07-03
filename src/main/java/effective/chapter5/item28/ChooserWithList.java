package effective.chapter5.item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChooserWithList<T> {
    private final List<T> choiceArray;

    public ChooserWithList(Collection<T> choices) {
        this.choiceArray = new ArrayList<>(choices);
    }
}
