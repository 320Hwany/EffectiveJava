# 아이템 23 - 태그 달린 클래스보다는 클래스 계층구조를 활용하라

2가지 이상의 의미를 표현할 수 있으며 그중 현재 표현하는 의미를 태그 값으로 알려주는 클래스가 있습니다.    
다음 코드는 원과 사각형을 표현할 수 있는 클래스입니다.    

## Figure - 태그 달린 클래스
````java
public class Figure {

    public enum Shape{ RECTANGLE, CIRCLE};

    // 현재 모양을 나타냅니다
    private final Shape shape;

    // 사각형일 때만 사용합니다
    private double length;

    private double width;

    // 원일 때만 사용합니다
    private double radius;

    public Figure(double length, double width) {
        this.shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    public Figure(double radius) {
        this.shape = Shape.CIRCLE;
        this.radius = radius;
    }

    public double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }

    public Shape getShape() {
        return shape;
    }

    ...
}
````

이 클래스는 단점이 한가득합니다. 우선 열거 타입 선언, 태그 필드, switch 문 등 쓸데 없는 코드가 많습니다.    
여러 구현이 한 클래스에 혼잡되어 있어 가독성도 나쁩니다. 또한 이러한 코드는 메모리도 많이 사용합니다.   
필드들을 final로 선언하려면 해당 의미에 쓰이지 않는 필드들까지 생성자에서 초기화해야 합니다.     
엉뚱한 필드를 초기화해도 런타임에야 문제가 드러나는 단점이 있습니다.   
즉, 태그 달린 클래스는 장황하고 오류를 내기 쉽고 비효율적입니다.    

## Figure - 클래스 계층 구조

공통적인 area 메소드만 Figure 추상 클래스에 정의하고 이를 상속받아서 확장할 수 있습니다.

````java
public abstract class Figure {

    public abstract double area();
}
````

### Rectangle
````java
public class Rectangle extends Figure {

    private final double length;

    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
````

### Circle
````java
public class Circle extends Figure {

    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * (radius * radius);
    }
}
````

## 정리

태그 달린 클래스를 써야 하는 상황은 거의 없다. 새로운 클래스를 작성하는데 태그 필드가 등장한다면   
태그를 없애고 계층구조로 대체하는 방법을 생각해보자. 기존 클래스가 태그 필드를 사용하고 있다면   
계층 구조로 리팩토링하는 걸 고민해보자

[아이템 23 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item23)               
[아이템 23 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item23) 
 


