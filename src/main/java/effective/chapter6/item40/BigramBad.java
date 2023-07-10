package effective.chapter6.item40;

public class BigramBad {

    private final char first;
    private final char second;

    public BigramBad(char first, char second) {
        this.first = first;
        this.second = second;
    }

    // Overriding이 아니라 Overloading을 해도 @Override를 사용하지 않으면 컴파일 에러가 발생하지 않는다
    public boolean equals(BigramBad b) {
        return b.first == first && b.second == second;
    }

    public int hashCode() {
        return 31 * first + second;
    }
}
