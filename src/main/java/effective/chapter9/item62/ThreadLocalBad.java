package effective.chapter9.item62;


import java.util.Map;

// 잘못된 예 - 문자열을 사용해 권한을 부여하였다
public class ThreadLocalBad {

    private ThreadLocalBad(){
    }

    public static void set(String key, Object value, Map<String, Object> keys) {
        keys.put(key, value);
    }

    public static Object get(String key, Map<String, Object> keys) {
        return keys.get(key);
    }
}
