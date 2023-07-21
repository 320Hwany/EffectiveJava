package effective.chapter8.item55;

import java.util.*;

public class OptionalUse {

    // 컬렉션에서 최댓값을 구한다 (컬렉션이 비었으면 예외를 던진다)
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("컬렉션이 비어있습니다");
        }

        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return result;
    }

    // 컬렉션에서 최댓값을 구해 Optional<E>로 반환한다
    public static <E extends Comparable<E>> Optional<E> maxWithOptional(Collection<E> c) {
        if (c.isEmpty()) {
            return Optional.empty();
        }

        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return Optional.of(result);
    }

    // 컬렉션에서 최댓값을 구해 Optional<E>로 반환한다 - 스트림 버전
    public static <E extends Comparable<E>> Optional<E> maxWithStream(Collection<E> c) {
        return c.stream().max(Comparator.naturalOrder());
    }

    public OptionalInt optionalInt(int a) {
        return OptionalInt.of(a);
    }

    public OptionalLong optionalLong(long a) {
        return OptionalLong.of(a);
    }

    public OptionalDouble optionalDouble(double a) {
        return OptionalDouble.of(a);
    }
}
