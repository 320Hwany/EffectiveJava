package effective.chapter5.item32;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UseVarargsMethod {

    private int count = 0;

    public void varargsMethod(String[]... varages) {
        for (String[] s : varages) {
            for (String s1 : s) {
                count++;
            }
        }
    }

    public void varargsMethodWithGeneric(List<String>... varages) {
        List<Integer> intList = List.of(42);
        Object[] objects = varages;
        objects[0] = intList;  // 힙 오염 발생
        String s = varages[0].get(0);  // ClassCastException 발생
    }

    public <T> T[] pickTwo1(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();
    }

    public <T> List<T> pickTwo2(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return List.of(a, b);
            case 1: return List.of(a, c);
            case 2: return List.of(b, b);
        }
        throw new AssertionError();
    }

    public <T> T[] toArray(T... args) {
        return args;
    }

    @SafeVarargs
    public static <T> List<T> flatten1(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public static <T> List<T> flatten2(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public int getCount() {
        return count;
    }
}
