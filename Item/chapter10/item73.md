# 아이템 73 - 추상화 수준에 맞는 예외를 던져라

수행하려는 일과 관련 없어 보이는 예외가 튀어나오면 당황스러울 것입니다.    
메소드가 저수준 예외를 처리하지 않고 바깥으로 전파해버릴 때 종종 일어나는 일입니다.    
사실 이는 단순히 프로그래머를 당황시키는 데 그치지 않고 내부 구현 방식을 드러내어 윗 레벨 API를 오염시킵니다.        
이 문제를 피하려면 상위 계층에서는 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 던져야 합니다.

### 예외 번역을 사용한 메소드

저수준 예외는 주로 외부 자원과의 상호작용에서 발생하며 고수준 예외는 애플리케이션 내에서 발생하는 예외로    
프로그램 논리와 관련되어있습니다.

````java
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
    
    ...
}
````

### 예외 연쇄를 확인하기 위한 메소드

예외를 번역할 때 저수준 예외가 디버깅에 도움이 된다면 예외 연쇄를 사용하는 게 좋습니다.    

````java
public class CustomException {

    ...

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
````

무턱대고 예외를 전파하는 것보다는 예외 번역이 우수한 방법이지만 그렇다고 남용해서는 안됩니다.        

## 정리

아래 계층의 예외를 예방하거나 스스로 처리할 수 없고, 그 예외를 상위 계층에 그대로 노출하기 곤란하다면    
예외 번역을 사용할 수 있습니다. 이때 예외 연쇄를 이용하면 상위 계층에는 맥락에 어울리는 고수준 예외를 던지면서     
근본 원인도 함께 알려주어 오류를 분석하기에 좋습니다.       

[아이템 73 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item73)                                                                                                
[아이템 73 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter10/item73)      
