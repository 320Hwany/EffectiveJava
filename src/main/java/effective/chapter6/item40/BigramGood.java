package effective.chapter6.item40;

import java.util.Objects;

// equals, hashCode 재정의시 @Override 를 사용함
public class BigramGood {

    private final char first;
    private final char second;

    public BigramGood(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigramGood that = (BigramGood) o;
        return first == that.first && second == that.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
