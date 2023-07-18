# 아이템 49 - 매개변수가 유효한지 검사하라

메소드와 생성자 대부분은 입력 매개변수의 값이 특정 조건을 만족하기를 바랍니다.    
예를들어 인덱스 값은 음수이면 안되며, 객체 참조는 null이 아니어야 합니다.   
이런 제약은 반드시 문서화해야 하며 메소드 몸체가 시작되기 전에 검사해야 합니다.     
즉 오류는 가능한 한 빨리 (발생한 곳에서) 잡아야 하는 것 입니다.   

매개변수 검사를 제대로 하지 못하면 몇가지 문제가 발생할 수 있습니다.   
1. 메소드가 수행되는 중간에 모호한 예외를 던지며 실패할 수 있다
2. 메소드가 잘 수행되지만 잘못된 결과를 반환할 수 있다
3. 메소드는 문제없이 수행됐지만 어떤 객체를 이상한 상태로 만들어놓아서 미래의 알 수 없는 시점에  
메소드와 관련 없는 오류를 낼 수 있다

자바 7에 추가된 java.util.Objects.requireNonNull 메소드는 유연하고 사용하기도 편하여   
더 이상 null 검사를 수동으로 하지 않아도 됩니다. 또한 원하는 예외 메세지도 지정할 수 있습니다.       

## requireNonNull 사용

```java
public Member(String name, int age) {
    Objects.requireNonNull(name, "이름을 입력해주세요");
    this.name = name;
    this.age = age;
}
```

반환 값을 사용하지 않고 순수한 null 검사 목적으로 사용해도 됩니다.    
이렇게 생성자 매개변수의 유효성 검사는 클래스 불변식을 어기는 객체가 만들어지지 않게 하는데           
꼭 필요합니다.        

## assert 사용

````java
protected void update(String name, int age) {
    assert name != null;
    assert age >= 0;
    this.name = name;
    this.age = age;
}
````

assert가 실패하면 AssertionError를 던지며 런타임에 아무런 효과도 성능저하도 없다는 특징이 있습니다.     

## 정리

메소드나 생성자를 작성할 때면 그 매개변수들에 어떤 제약이 있을지 생각해야 합니다.    
그 제약들을 문서화하고 메소드 코드 시작 부분에서 명시적으로 검사해야 합니다.    
이러한 습관은 유효성 검사가 실제 오류를 처음 걸러낼 때 충분히 가치가 있습니다.           

[아이템 49 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item49)                                                                                  
[아이템 49 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item49)          