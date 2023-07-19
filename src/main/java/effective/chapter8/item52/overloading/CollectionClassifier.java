package effective.chapter8.item52.overloading;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// 컬렉션 분류기 - 오류!
public class CollectionClassifier {

    public static String classify(Set<?> s) {
        return "집합";
    }

    public static String classify(List<?> lst) {
        return "리스트";
    }

    public static String classify(Collection<?> c) {
        return "그 외";
    }
}
