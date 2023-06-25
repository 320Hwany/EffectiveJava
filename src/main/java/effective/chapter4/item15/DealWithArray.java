package effective.chapter4.item15;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DealWithArray {

    // 불변일 것 같지만 수정이 가능하다
    public static final int[] valuesBadWay = {1, 2, 3, 4, 5};

    // Good way - 1
    private static final Integer[] PRIVATE_VALUES1 = {1, 2, 3, 4, 5};

    public static final List<Integer> valuesGoodWay1 =
            Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES1));

    // Good way - 2
    private static final int[] PRIVATE_VALUES2 = {1, 2, 3, 4, 5};

    public static final int[] valuesGoodWay2() {
        return PRIVATE_VALUES2.clone();
    }
}
