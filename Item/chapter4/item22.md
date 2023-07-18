# 아이템 22 - 인터페이스는 타입을 정의하는 용도로만 사용하라

인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 합니다.   
즉 클래스가 어떤 인터페이스를 구현한다는 것은 자신의 인스턴스로 무엇을 할 수 있는지를 클라이언트에게   
이야기 해주는 것입니다. 인터페이스는 오직 이 용도로만 사용해야 합니다.   

## 상수 인터페이스 - 안티패턴

상수 인터페이스는 인터페이스를 잘못 사용한 예입니다.    
상수 인터페이스는 내부 구현을 클래스의 API로 노출하는 행위입니다.   
더 심하게는 클라이언트 코드가 내부 구현에 해당하는 이 상수들에 종속되게 합니다.

### HelloInterface
````java
// 상수 인터페이스 - 안티패턴
public interface HelloInterface {

    String hello = "hello";

    String world = "world";
}
````

## 상수 유틸리티 클래스 

상수 관리는 인스턴스화를 방지해야 합니다.   
하지만 이때도 리플렉션으로는 인스턴스화를 할 수 있습니다.   

### HelloConstant
````java
public class HelloConstant {

    // 인스턴스화 방지
    private HelloConstant() {
    }

    public static final String hello = "hello";

    public static final String world = "world";
}
````

### HelloConstantSingleton

enum 클래스로 상수를 관리한다면 완벽한 싱글톤을 보장할 수 있습니다.   
````java
public enum HelloConstantSingleton {

    HELLO("hello"),
    WORLD("world");

    private final String value;

    HelloConstantSingleton(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
````

## 정리

인터페이스는 타입을 정의하는 용도로만 사용해야 합니다. 상수 공개용 수단으로는 사용하지말고   
이때는 인스턴스화를 방지한 클래스를 이용하거나 enum 클래스를 사용해야 합니다    

[아이템 22 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item22)               
[아이템 22 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item22)     