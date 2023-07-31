package effective.chapter10.item75;

public class CustomIndexOutOfBoundsException extends RuntimeException {

    private static final String MESSAGE = "최솟값: %d, 최댓값: %d, 인덱스: %d";
    private final String detailMessage;

    public CustomIndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
        this.detailMessage = String.format(MESSAGE, lowerBound, upperBound, index);
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
