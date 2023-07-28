# 아이템 70 - 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라

자바는 문제 상황을 알리는 타입(throwable)으로 검사 예외, 런타임 예외, 에러, 이렇게 3가지를 제공합니다.     
언제 무엇을 사용해야 하는 지를 말해주는 멋진 지침들이 있습니다.        

## 호출하는 쪽에서 복구하리라 여겨지는 상황이라면 검사 예외를 사용하자

검사 예외를 던지면 호출자가 그 예외를 catch로 잡아 처리하거나 더 바깥으로 전파하도록 강제하게 됩니다.    
이때 사용자는 예외를 잡기만 하고 별다른 조치를 취하지 않을 수도 있지만 이는 보통 좋지 않은 생각입니다.    

````java
public class CustomException {

    // 검사 예외는 catch 하거나 throws 해야합니다.
    public void makeCheckedException() throws IOException {
        throw new IOException();
    }

    ...
}
````

## 프로그래밍 오류를 나타낼 때는 런타임 예외를 사용하라

런타임 예외의 대부분은 전제조건을 만족하지 못했을 때 발생합니다.      
예외를 catch로 잡아 처리하거나 더 바깥으로 전파하도록 강제하지 않습니다.         

````java
public class CustomException {
    
    ...

    // 비검사 예외는 catch 하거나 throws를 강제하지 않습니다
    public void makeUncheckedException() {
        throw new RuntimeException();
    }
}
````

복구 가능하다고 생각하면 검사 예외를 그렇지 않다면 런타임 예외를 사용해야 합니다.   
확신하기 어렵다면 아마도 런타임 예외를 선택하는 편이 나을 것입니다.    

에러는 보통 JVM이 자원 부족, 불변식 깨짐 등 더 이상 수행을 계속할 수 없는 상황을 나타낼 때 사용합니다.    
우리가 구현하는 비검사 throwable은 모두 RuntimeException의 하위 클래스여야 합니다.    
Error는 상속하지 말아야 할 뿐만 아니라 throw 문으로 직접 던지는 일도 없어야 합니다.     

## 정리

복구할 수 있는 상황이라면 검사 예외를, 프로그래밍 오류라면 비검사 예외를 던져야 합니다.   
확실하지 않다면 비검사 예외를 던지는게 낫습니다.     
검사 예외도 아니고 런타임 예외도 아닌 throwable은 정의하지 말아야 합니다.    
검사 예외라면 복구에 필요한 정보를 알려주는 메소드도 정의해야 합니다.     

[아이템 70 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item70)                                                                                            
[아이템 70 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter10/item70)        