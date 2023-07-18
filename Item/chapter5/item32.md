# 아이템 32 - 제네릭과 가변인수를 함께 쓸 때는 신중하라

가변인수 메소드와 제네릭은 자바 5때 함께 추가되었지만 서로 잘 어우러지지는 않습니다.    
가변인수 메소드를 호출하면 가변인수를 담기 위한 배열이 자동으로 하나 만들어집니다.   
내부로 감춰야 했을 이 배열을 클라이언트에 노출하는 문제가 생겼습니다.   
그 결과 varargs 매개변수에 제네릭이나 매개변수화 타입이 포함되면 알기 어려운 컴파일 경고가 발생합니다.    

## 제네릭과 varargs를 혼용하면 타입 안전성이 깨진다

````java
public void varargsMethodWithGeneric(List<String>... varages) {
    List<Integer> intList = List.of(42);
    Object[] objects = varages;
    objects[0] = intList;  // 힙 오염 발생
    String s = varages[0].get(0); // ClassCastException 발생
}
````
매개변수화 타입의 변수가 타입이 서로 다른 객체를 참조하면 힙오염이 발생합니다.  
이렇게 타입 안전성이 깨지니 제네릭 varargs 배열 매개변수에 값을 저장하는 것은 안전하지 않습니다.    

## 제네릭과 varargs 매개변수를 안전하게 사용하는 방법

가변인수를 사용해야 한다면 @SafeVarargs 애너테이션은 메소드 작성자가 그 메소드가 타입 안전함을 보장하는 장치입니다.       
메소드가 이 배열에 아무것도 저장하지 않고 그 배열의 참조가 밖으로 노출되지 않는다면 타입 안전합니다.        
즉 다음 두 가지 조건을 만족해야 합니다.   
1. varargs 매개변수 배열에 아무것도 저장하지 않는다
2. 그 배열을 신뢰할 수 없는 코드에 노출하지 않는다

````java
@SafeVarargs
public static <T> List<T> flatten1(List<? extends T>... lists) {
    List<T> result = new ArrayList<>();
    for (List<? extends T> list : lists) {
        result.addAll(list);
    }
    return result;
}
````

## 제네릭 varargs 매개변수를 List로 대체한 예시 - 타입 안전하다
````java
public static <T> List<T> flatten2(List<List<? extends T>> lists) {
    List<T> result = new ArrayList<>();
    for (List<? extends T> list : lists) {
        result.addAll(list);
    }
    return result;
}
````

## 정리

가변인수와 제네릭은 궁합이 좋지 않습니다. 가변인수 기능은 배열을 노출하여 추상화가 완벽하지 못하고    
배열과 제네릭의 타입 규칙이 서로 다르기 때문입니다.      
제네릭 varargs 매개변수는 타입 안전하지 않지만 허용됩니다. 메소드에 제네릭 varargs 매개변수를   
사용하고자 한다면 먼저 그 메소드가 타입 안전한지 확인한 후 @SafeVarargs 애너테이션을 달아줘야 합니다.   
이 외에도 가변인수를 사용하지 않고 List를 사용하여 타입 안전성을 보장할 수 있습니다.    

[아이템 32 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item32)                                         
[아이템 32 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item32)          