package effective.chapter7.item44;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

// LinkedHashMap을 상속 받아 함수 객체를 받는 생성자를 제공한 Custom 클래스
public class CustomLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final BiPredicate<Map<K, V>, Map.Entry<K, V>> removalFunction;

    public CustomLinkedHashMap(BiPredicate<Map<K, V>, Map.Entry<K, V>> removalFunction) {
        this.removalFunction = removalFunction;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return removalFunction.test(this, eldest);
    }

    // 예시로 사용해보기 위한 메소드
    public static <K, V> CustomLinkedHashMap<K, V> createCache(BiPredicate<Map<K, V>,
            Map.Entry<K, V>> removalFunction) {
        return new CustomLinkedHashMap<>(removalFunction);
    }
}
