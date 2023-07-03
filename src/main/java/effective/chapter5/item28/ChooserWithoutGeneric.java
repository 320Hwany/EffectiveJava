package effective.chapter5.item28;

import java.util.Collection;

public class ChooserWithoutGeneric {
    private final Object[] choiceArray;

    public ChooserWithoutGeneric(Collection choices) {
        this.choiceArray = choices.toArray();
    }
}
