# 아이템 17 - 변경 가능성을 최소화하라

## 불변 클래스 

불변 클래스란 그 인스턴스의 내부 값을 수정하는 클래스를 말합니다.   
불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않습니다.    
자바에서 대표적인 불변 클래스로는 String, 기본 타입의 박싱된 클래스, BigInteger, BigDecimal 등이 있습니다.        

먼저 불변 클래스는 쓰레드 세이프하여 동기화할 필요가 없습니다.   
따라서 불변 객체는 안심하고 공유할 수 있습니다.

클래스를 불변으로 만들려면 다음 다섯가지 규칙을 따라야 합니다.   

1. 객체의 상태를 변경하는 메소드(변경자)를 제공하지 않는다. 
2. 클래스를 확장할 수 없도록 한다. 
3. 모든 필드를 final로 선언한다.
4. 모든 필드를 private으로 선언한다.
5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.    

### Calculator
````java
public class Calculator {

    private final int a;

    private final int b;

    private Calculator(final int a, final int b) {
        this.a = a;
        this.b = b;
    }

    public static Calculator valueOf(final int a, final int b) {
        return new Calculator(a, b);
    }

    public Calculator plus(Calculator c) {
        return new Calculator(a + c.getA(), b + c.getB());
    }

    public Calculator minus(Calculator c) {
        return new Calculator(a - c.getA(), b - c.getB());
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
````

위와 같이 5가지 규칙을 만족하는 불변 클래스를 만들었습니다.    
이때 plus(), minus() 메소드처럼 인스턴스 자신은 수정하지 않고 새로운 인스턴스를 만들어서 반환합니다.   
이러한 방식을 함수형 프로그래밍이라고 합니다.    

클래스를 final로 선언하여 불변으로 만들 수도 있지만 정적 팩토리를 사용하여 구현할 수도 있습니다.   
또한 자바 14부터 'record'가 도입되어 불변 클래스를 쉽게 생성할 수 있습니다.   

## 정리

불변 클래스는 값이 다르면 반드시 독립된 객체로 만들어야 한다는 단점도 있지만    
장점이 더 많으므로 꼭 필요한 경우가 아니라면 불변으로 생성해야 합니다.    
불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소한으로 줄여야 합니다.    

[아이템 17 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item17)      
[아이템 17 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item17)      