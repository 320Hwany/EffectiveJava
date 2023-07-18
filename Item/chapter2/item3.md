# 아이템 3 - private 생성자나 열거 타입으로 싱글톤임을 보증하라

싱글톤이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말합니다.   

1. public static final 필드 방식

```java
public class Member1 {

    public static final Member1 INSTANCE = new Member1();

    private Member1() {
    }
}
```

2. 정적 팩토리 메소드를 public static 멤버로 제공하는 방식

```java
public class Member2 {

    private static final Member2 INSTANCE = new Member2();

    private Member2() {
    }


    public static Member2 getInstance() {
        return INSTANCE;
    }
}
```

둘 중 하나의 방식으로 만든 싱글톤 클래스를 직렬화하려면 단순히 Serializable을 구현한다고   
선언하는 것만으로는 부족합니다. 모든 인스턴스 필드를 transient라고 선언하고 readResovle 메소드를 제공해야 합니다.   
이렇게 하지 않으면 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어집니다.    

3. 원소가 하나뿐인 열거 타입 방식

```java
public enum Member3 {

    INSTANCE;

    public String getName() {
        return "이름";
    }

    public int getAge() {
        return 20;
    }
}
```

```java
public enum Member4 {

    INSTANCE;

    public static final String name = "이름";

    public static final int age = 20;
}
```



public 필드 방식과 비슷하지만 더 간결하고 추가 노력 없이 직렬화할 수 있고 아주 복잡한 직렬화 상황이나    
리플렉션 공격에서도 제2의 인스턴스가 생기는 일을 완벽히 막아줍니다.    

## 정리

대부분 상황에서는 원소가 하나뿐인 열거 타입 (3번 방식)이 싱글톤을 만드는 가장 좋은 방법입니다.   

[아이템 3 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item3)                  
[아이템 3 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter2/item3)    
