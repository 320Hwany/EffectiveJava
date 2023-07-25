# 아이템 63 - 문자열 연결은 느리니 주의하라

문자열 연결 연산자(+)는 여러 문자열을 하나로 합쳐주는 편리한 수단입니다.     
그런데 한 줄자리 출력값 혹은 작고 크기가 고정된 객체의 문자열 표현을 만들 때라면 괜찮지만    
본격적으로 사용하기 시작하면 성능 저하를 피하기 어렵습니다.     
문자열 연결 연산자로 문자열 n개를 잇는 시간은 n^2에 비례합니다.    
문자열은 불변이라서 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야 하므로 성능 저하를 피할 수 없습니다.    

### 문자열 연결을 잘못 사용한 예 - 느리다!

````java
public String statement1() {
    String result = "";
    for (int i = 0; i < numItems(); i++) {
        result += items.get(i);
    }
    return result;
}
````

### StringBuilder를 사용하면 문자열 연결 성능이 크게 개선된다

성능을 포기하고 싶지 않다면 String 대신 StringBuilder를 사용하면 됩니다.    

````java
public String statement2() {
    StringBuilder b = new StringBuilder(numItems() * 5);
    for (int i = 0; i < numItems(); i++) {
        b.append(items.get(i));
    }
    return b.toString();
}
````

하지만 자바 버전이 올라가면서 컴파일러가 문자열 연산에 대해 점점 더 최적화를 해줍니다.    
따라서 String 문자열 연결 연산자로 작성하더라도 하나의 String 문자열로 인식하거나       
컴파일 시점에 StringBuilder로 바꿔줍니다.    
하지만 아직 완벽하진 않고 최적화를 못하는 경우도 있기 때문에 가능하면 문자열 연결 연산은 피해야 합니다.         

## 정리

성능에 신경 써야 한다면 많은 문자열을 연결할 때는 문자열 연결 연산자(+)를 피해야 합니다.     
문자 배열을 사용하거나, 문자열을 하나씩 처리하는 방법도 있습니다.      

[아이템 63 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item63)                                                                                                            
[아이템 63 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item63)       

