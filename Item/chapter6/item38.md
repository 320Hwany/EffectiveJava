# 아이템 38 - 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라

열거 타입은 거의 모든 상황에서 타입 안전 열거 패턴보다 우수합니다.   
하지만 타입 안전 열거 패턴은 확장할 수 있으나 열거 타입 자체는 그럴 수 없습니다.      

다행히 열거 타입으로 확장을 할 수 있는 방법이 존재하는데 바로 열거 타입이 임의의 인터페이스를   
구현할 수 있다는 사실을 이용하는 것입니다.     

예를들어 연산 코드를 생각해보겠습니다. 만약 Operation 열거 타입안에 +, -, *, / 와 같은 연산자들을   
정의해놓았는데 시간이 지나서 ^, % 도 필요하다면 기존 열거 타입에 추가하는 방법으로 해야합니다.    
이때 더 좋은 방법이 존재하는데 Operation 인터페이스를 정의하고 이를 구현하는 열거 타입을 만드는 것입니다.   

## Operation

````
public interface Operation {

    double apply(double x, double y);
}
````

## BasicOperation

````
public enum BasicOperation implements Operation {

    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
````

## ExtendedOperation

````
public enum ExtendedOperation implements Operation {

    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
````

이 방법은 열거 타입에 따로 추상 메소드를 선언하지 않아도 됩니다.   
상수별 메소드 구현과는 다른 점이라고 할 수 있습니다.    

이제 클라이언트 코드를 한번 살펴보겠습니다.    

## Client

````
public class Client {

    ...
    
    private static <T extends Enum<T> & Operation> List<Double> operation1(Class<T> opEnumType, double x, double y) {
        List<Double> result = new ArrayList<>();
        for (Operation op : opEnumType.getEnumConstants()) {
            result.add(op.apply(x, y));
        }
        return result;
    }

    private static List<Double> operation2(Collection<? extends Operation> opSet, double x, double y) {
        List<Double> result = new ArrayList<>();
        for (Operation op : opSet) {
            result.add(op.apply(x, y));
        }
        return result;
    }
}
````

클라이언트 코드에서 BasicOperation인지 ExtendedOperation인지 알 수 없습니다.    
컴파일타임에 오직 Operation을 매개변수로 받는다는 사실만 알고 런타임 시점에 BasicOperation, ExtendedOperation을    
결정하여 apply 메소드를 실행합니다.     
다만 여기에도 사소한 문제가 존재하는데 바로 열거 타입끼리 구현을 상속할 수 없다는 점입니다.       

## 정리

열거 타입 자체는 확장할 수 없지만 인터페이스와 그 인터페이스를 구현하는 기본 열거 타입을 함께 사용해 같은 효과를 낼 수 있습니다.    
이렇게 하면 클라이언트는 이 인터페이스를 구현해 자신만의 열거 타입을 만들 수 있습니다.    
그리고 API가 인터페이스 기반으로 작성되었다면 기본 열거 타입의 인스턴스가 쓰이는 모든 곳을 새로 확장한 열거 타입의    
인스턴스로 대체해 사용할 수 있습니다.    

[아이템 38 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item38)                                                        
[아이템 38 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item38)            