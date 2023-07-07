# 아이템 34 - int 상수 대신 열거 타입을 사용하라

열거 타입은 일정 개수의 상수 값을 정의한 다음 그 외의 값은 허용하지 않는 타입입니다.                       
사계절, 태양계의 행성, 카드게임의 카드 종류 등이 좋은 예입니다.        
자바에서 열거 타입을 지원하기 전에는 다음 코드처럼 정수 상수를 한 묶음 선언해서 사용하곤 했습니다.      

## 정수 열거 패턴 - 상당히 취약하다
````
public class IntEnumPattern {

    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;
}
````

위 코드의 문제점은 사과를 넣어야 하는데 오렌지 관련 정보를 넣어도 컴파일러가 잡지 못합니다.           
````
int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;
````

열거 타입은 컴파일 타임 타입 안전성을 제공합니다.         

## 데이터와 메소드를 갖는 열거 타입

각 열거 타입 상수 오른쪽 괄호 안 숫자는 생성자에 넘겨지는 매개변수입니다.     
열거 타입 상수 각각을 특정 데이터와 연결지으려면 생성자에서 데이터를 인스턴스 필드에    
저장하면 됩니다.     

````
public enum Planet {

    MERCURY(3.302e+23, 2.4392e6),
    VENUS(4.869E+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7);

    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }
}
````

## 상수별 메소드 구현을 활용한 열거 타입

열거 타입은 상수별로 다르게 동작하는 코드를 구현하는 더 나은 수단을 제공합니다.     
열거 타입에 apply 라는 추상 메소드를 선언하고 각 상수별 class body,     
즉 각 상수에서 자신에 맞게 재정의하면 됩니다.      

````
public enum Operation {

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

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract double apply(double x, double y);
    
    ...
}
````

## 열거 타입용 fromString 메소드 구현

상수를 받아서 해당 열거 타입 상수로 변환해주는 fromString을 구현할 수 있습니다.      

````
public enum Operation {

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

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract double apply(double x, double y);

    // 추가된 부분
    private static final Map<String, Operation> stringToEnum =
            Arrays.stream(values()).collect(Collectors.toMap(op -> op.symbol, op -> op));

    // 추가된 부분
    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
````

## 전략 열거 타입 패턴
 
상수별 메소드 구현을 활용하여 정의하기에는 너무 중복되는 코드가 많고     
switch 문을 사용하기에는 유연성이 떨어집니다.    
이러한 경우에 대비하여 enum PayType을 정의하여 전략 열거 타입 패턴을 사용할 수 있습니다.     

````
public enum PayrollDay {

    MONDAY(WEEKDAY),
    TUESDAY(WEEKDAY),
    WEDNESDAY(WEEKDAY),
    THURSDAY(WEEKDAY),
    FRIDAY(WEEKDAY),
    SATURDAY(WEEKEND),
    SUNDAY(WEEKEND);

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    public PayType getPayType() {
        return payType;
    }

    enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 : (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };

        abstract int overtimePay(int minsWorked, int payRate);

        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay(minsWorked, payRate);
        }
    }
}
```` 

## 정리

필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 항상 열거 타입을 사용해야 합니다.    
열거 타입은 확실히 정수 상수보다 뛰어납니다. 더 읽기 쉽고 안전하고 강력합니다.    
대다수 열거 타입이 명시적 생성자나 메소드 없이 쓰이지만 각 상수를 특정 데이터와 연결 짓거나     
상수마다 다르게 동작하게 할 때는 필요합니다.
드물게는 하나의 메소드가 상수별로 다르게 동작해야 할 때가 있습니다.    
이런 열거 타입에서는 switch 문 대신 상수별 메소드 구현을 사용합니다.   
열거 타입 상수 일부가 같은 동작을 공유한다면 전략 열거 타입 패턴을 사용할 수 있습니다.       

[아이템 34 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6)                                                   
[아이템 34 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item34)      


