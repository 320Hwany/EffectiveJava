# 아이템 75 - 예외의 상세 메세지에 실패 관련 정보를 담으라

예외를 잡지 못해 프로그램이 실패하면 자바 시스템은 그 예외의 스택 추적 정보를 자동으로 출력합니다.    
스택 추적은 예외 객체의 toString 메소드를 호출해 얻는 문자열로 보통은 예외의 클래스 이름 뒤에   
상세 메세지가 붙는 형태입니다. 만약 실패를 재현하기 어렵다면 더 자세한 정보를 얻기가 어렵거나 불가능합니다.    
따라서 예외의 toString 메소드에 실패 원인에 관한 정보를 가능한 한 많이 담아 반환하는 일은 아주 중요합니다.

### CustomIndexOutOfBoundsException

````java
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
````

실패 순간을 포착하려면 발생한 예외에 관여된 모든 매개변수와 필드의 값을 실패 메세지에 담아야 합니다.
기존의 IndexOutOfBoundsException를 변형해서 CustomIndexOutOfBoundsException을 만들어보았습니다.   

이때 예외의 상세 메세지와 최종 사용자에게 보여줄 오류 메세지를 혼동해서는 안됩니다.    
최종 사용자에게는 친절한 안내 메세지를 보여줘야 하는 반면 예외 메세지는 가독성보다는 담긴 내용이 훨씬 중요합니다.    

## 정리

예외의 상세 메세지에 실패 원인에 관한 정보를 가능한 한 많이 담아 반환해야 합니다.   
하지만 이는 예외를 분석하는 용도이지 최종 사용자에게 보여줄 친절한 오류 메세지와는 다릅니다.    

[아이템 75 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item75)                                                                                                 
[아이템 75 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter10/item75)           