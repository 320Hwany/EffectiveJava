package effective.chapter9.item62;


import java.util.Map;

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
