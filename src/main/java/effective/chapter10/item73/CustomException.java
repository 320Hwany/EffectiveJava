package effective.chapter10.item73;


import java.util.NoSuchElementException;

// 예외 번역을 사용한 클래스
public class CustomException {

    public void exceptionTranslation1() {
        try {
            // 저수준 예외인 NoSuchElementException 가 발생할 수 있는 코드
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            // 저수준 예외 -> 고수준 예외
            throw new IndexOutOfBoundsException();
        }
    }

    // 예외 연쇄를 확인하기 위한 메소드
    public HigherLevelException exceptionTranslation2() {
        try {
            // 저수준 예외인 NoSuchElementException 가 발생할 수 있는 코드
            throw new NoSuchElementException();
        } catch (NoSuchElementException cause) {
            // 저수준 예외 -> 고수준 예외
            return new HigherLevelException(cause);
        }
    }
}
