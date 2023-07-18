# 아이템 13 - clone 재정의는 주의해서 진행하라

Cloneable은 복제해도 되는 클래스임을 명시하는 용도의 mixin interface지만 의도한 목적을 제대로 이루지는 못했습니다.    
가장 큰 문제는 clone 메소드가 선언된 곳이 Cloneable이 아닌 Object이고 그마저도 protected라는 점입니다.    

Cloneable 인터페이스는 Object의 protected 메소드인 clone의 동작 방식을 결정합니다.   
Cloneable을 구현한 클래스는 clone 메소드를 public으로 제공하며 사용자는 당연히 복제가 제대로 이뤄지리라고 기대하지만    
이를 만족하려면 복잡하고 모순적인 상황이 발생합니다.       

## 가변 상태를 참조하지 않은 클래스용 clone 메소드

모든 필드가 기본 타입이거나 불변 객체를 참조하는 clone 메소드는 다음과 같이 구현할 수 있습니다.    
```java
@Override
public Member clone() {
    try {
        Member clone = (Member) super.clone();
        return clone;
    } catch (CloneNotSupportedException e) {
          throw new AssertionError();
    }
}
```
하지만 불변 클래스는 굳이 clone 메소드를 제공하지 않는 것이 좋습니다.    

## 가변 상태를 참조하는 클래스용 clone 메소드

elements는 Object 배열이고 배열을 복제할 때는 유일하게 clone 메소드 사용을 권장합니다.     

```java
@Override
public Stack clone() {
    try {
        Stack result = (Stack) super.clone();
        result.elements = elements.clone();
        return result;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError();
    }
}
```

clone을 재귀적으로 호출하는 방법 이외에도 깊은 복사를 하기위해 반복자를 써서 순회하는 방법도 있습니다.    
하지만 이러한 방법은 복잡하고 불필요한 검사 예외도 던지며 형변환도 필요하는 등 여러 단점이 있습니다.    

## 정리

새로운 인터페이스, 새로운 클래스를 만들 때는 Cloneable을 확장해서는 안됩니다.  
복제를 할 때의 기본 원칙은 생성자와 팩토리를 사용하는 것이 훨씬 더 좋은 방법입니다.    
단, 배열만은 clone 메소드 방식이 가장 깔끔한 방식입니다.    

[아이템 13 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter3/item13)           
[아이템 13 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter3/item13)



