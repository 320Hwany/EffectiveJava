package effective.chapter10.item73;

public class HigherLevelException extends RuntimeException {

    // 예외 연쇄용 생성자
    public HigherLevelException(Throwable cause) {
        super(cause);
    }
}
