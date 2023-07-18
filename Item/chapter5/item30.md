# 아이템 30 - 이왕이면 제네릭 메소드로 만들라

클래스와 마찬가지로 메소드도 제네릭으로 만들 수 있습니다.   
매개변수화 타입을 받는 정적 유틸리티 메소드는 보통 제네릭입니다.   
예를들어 Collections의 binarySearch, sort 등은 모두 제네릭입니다.   

### 로 타입 사용

컴파일은 되지만 잠재적 위험을 가지고 있습니다.   
````java
public static Set union(Set s1, Set s2) {
    Set result = new HashSet(s1);
    result.addAll(s2);
    return result;
}
````

### 제네릭 메소드 

타입 매개변수는 <E>이고 반환 타입은 Set<E>인 제네릭 메소드로 수정할 수 있습니다.   
````java
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
````

## 제네릭 싱글톤

IDENTITY_FN를 UnaryOperator<T>로 형변환하면 비검사 형변환 경고가 발생합니다.   
`UnaryOperator<Object>`는 `UnaryOperator<T>`가 아니기 때문입니다.   
하지만 항등함수란 입력 값을 수정없이 그대로 반환하는 특별한 함수이기 때문에   
T가 어떤 타입이든 `UnaryOperator<T>` 를 사용해도 타입 안전합니다.   

````java
public class GenericMethod {

    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }
}
````

## 재귀적 타입 한정

재귀적 타입 한정은 주로 타입의 자연적 순서를 정하는 Comparable 인터페이스와 함께 쓰입니다.   
````java
// 모든 타입 E는 자신과 비교할 수 있다
public static <E extends Comparable<E>> E max(Collection<E> c) {
    if (c.isEmpty()) {
        throw new IllegalArgumentException("컬렉션이 비어있습니다");
    }

    E result = null;
    for (E e : c) {
        if (result == null || e.compareTo(result) > 0) {
            result = Objects.requireNonNull(e);
        }
    }

    return result;
}
````

## 정리

제네릭 타입과 마찬가지로 클라이언트에서 입력 매개변수와 반환 값을 명시적으로 형변환해야 하는  
메소드보다 제네릭 메소드가 더 안전하며 사용하기도 쉽습니다.   
메소드도 형변환 없이 사용할 수 있는 편이 좋으며 많은 경우에 그렇게 하려면 제네릭 메소드가 되어야 한다.            

[아이템 30 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item30)                          
[아이템 30 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item30)          