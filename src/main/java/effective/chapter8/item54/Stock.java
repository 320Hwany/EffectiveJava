package effective.chapter8.item54;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stock {

    private List<String> stringInStock = new ArrayList<>();

    // 컬렉션이 비었으면 null을 반환한다 - 따라 하지 말것
    public List<String> getStringsBad() {
        return stringInStock.isEmpty() ? null : new ArrayList<>(stringInStock);
    }

    // 빈 컬렉션을 반환하는 올바른 예
    public List<String> getStringsBetter() {
        return new ArrayList<>(stringInStock);
    }

    // 최적화 - 빈 컬렉션을 매번 새로 할당하지 않도록 함
    public List<String> getStringOptimization() {
        return stringInStock.isEmpty() ? Collections.emptyList()
                : new ArrayList<>(stringInStock);
    }

    // 길이가 0일수도 있는 배열을 반환하는 올바른 방법
    public String[] getStringArray() {
        return stringInStock.toArray(new String[0]);
    }

    // 최적화 - 빈 배열을 매번 새로 할당하지 않도록 함 (항상 최선은 아니다)
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public String[] getStringArrayOptimization() {
        return stringInStock.toArray(EMPTY_STRING_ARRAY);
    }
}
