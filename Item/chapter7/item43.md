# 아이템 43 - 람다보다는 메소드 참조를 사용하라

람다가 익명 클래스보다 나은 점 중에서 가장 큰 특징은 간결함입니다.    
그런데 자바에는 함수 객체를 심지어 람다보다도 더 간결하게 만드는 방법이 있습니다.   
바로 메소드 참조입니다.     

## 메소드 참조의 사용이 좋은 경우

````java
map.merge(key, 1, (count, incr) -> count + incr);
````

위와 같이 람다로 표현한 식에서 매개변수인 count와 incr는 크게 하는일 없이 공간을 꽤 차지합니다.   
자바 8이 되면서 Integer 클래스(와 모든 기본 타입의 박싱 타입)는 이 람다와 기능이 같은 정적 메소드   
sum을 제공하기 시작했습니다.   

````java
map.merge(key, 1, Integer::sum);
````

매개변수 수가 늘어날수록 메소드 참조로 제거할 수 있는 코드양도 늘어납니다.   
따라서 람다로 구현했을 때 길이가 너무 길거나 복잡하다면 메소드 참조가 좋은 대안이 될 수 있습니다.   

## 메소드 참조가 오히려 가독성을 떨어뜨리는 경우

하지만 메소드 참조가 항상 가독성을 좋게하는 것은 아닙니다.   

````java
service.execute(GoshThisClassNameIsHumongous::action);
````

````java
service.execute(() -> action());
````

위와 같이 단순히 람다로 표현하는 것이 더 짧고 명확한 경우도 있습니다.   

## 대표적인 메소드 참조 유형

대표적인 사례들로 람다로 표현하는 방식과 메소드 참조 방식을 비교해보겠습니다.       

1. 정적 메소드 참조

````java
Integer::parseInt

str -> Integer.parseInt(str)
````

2. 한정적 (인스턴스) 메소드 참조

````java
Instant.now()::isAfter

Instant then = Instant.now();
t -> then.isAfter(t);
````

3. 비한정적 (인스턴스) 메소드 참조

````java
String::toLowerCase

str -> str.toLowerCase()
````

4. 클래스 생성자 메소드 참조

````java
TreeMap<K,V>::new

() -> new TreeMap<K,V>()
````

5. 배열 생성자

````java
int[]::new

len -> new int[len}
````

## 정리

메소드 참조는 람다의 간단명료한 대안이 될 수 있습니다. 메소드 참조 쪽이 짧고 명확하다면   
메소드 참조를 쓰고 그렇지 않을 때만 람다를 사용해야 합니다.        

[아이템 43 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item43)            