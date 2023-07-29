# 아이템 72 - 표준 예외를 사용하라

자바 라이브러리는 대부분 API에서 쓰기에 충분한 수의 예외를 제공합니다.    
표준 예외를 재사용하면 얻는 게 많습니다. 우리가 만든 API가 다른 사람이 익히고 사용하기 쉬워집니다.      
또한 예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸립니다.     

Exception, RuntimeException, Throwable, Error는 직접 재사용하지 말아야합니다.     
이 클래스들은 추상 클래스라고 생각해야 합니다.     
이 예외들은 다른 예외들의 상위 클래스이므로 여러 성격의 예외들을 포괄하는 클래스이므로 안정적으로 테스트할 수 없습니다.     

다음은 널리 재사용되는 예외들을 모아놓은 표준 예외를 사용하는 클래스입니다.      

### 표준 예외를 사용하는 클래스
````java
public class CustomException {

    public void makeIllegalArgumentException() {
        // 허용되지 않는 값이 인수로 건네졌을 때
        throw new IllegalArgumentException();
    }

    public void makeIllegalStateException() {
        // 객체가 메소드를 수행하기에 적절하지 않은 상태일 때
        throw new IllegalStateException();
    }

    public void makeNullPointerException() {
        // null을 허용하지 않는 메소드에 null을 건넸을 때
        throw new NullPointerException();
    }

    public void makeIndexOutOfBoundsException() {
        // 인덱스가 범위를 넘어섰을 때
        throw new IndexOutOfBoundsException();
    }

    public void makeConcurrentModificationException() {
        // 허용하지 않는 동시 수정이 발견됐을 때
        throw new ConcurrentModificationException();
    }

    public void makeUnsupportedOperationException() {
        // 호출한 메소드를 지원하지 않을 때
        throw new UnsupportedOperationException();
    }
}
````

더 많은 정보를 제공하기를 원한다면 표준 예외를 확장하는 것도 좋습니다.   
단 예외는 직렬화할 수 있다는 사실을 기억하고 직렬화에는 많은 부담이 따른다는 것도 기억해야 합니다.     

[아이템 72 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item72)            
