# 아이템 19 - 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라

상속용 클래스는 재정의할 수 있는 메소드들을 내부적으로 어떻게 이용하는지 문서로 남겨야 한다.   
API 문서의 메소드 설명 끝에서 종종 "Implementation Requirements"로 시작하는 것을 볼 수 있는데    
그 메소드의 내부 동작 방식을 설명하는 곳입니다.   
이렇게 클래스를 안전하게 상속할 수 있도록 하려면 내부 구현 방식을 설명해야 합니다.    

상속용 클래스를 시험하는 방법은 직접 하위 클래스를 만들어보는 것이 유일합니다.   
따라서 상속용으로 설계한 클래스는 반드시 하위 클래스를 만들어 검증해야 합니다.    

상속을 허용하는 클래스가 지켜야 할 규칙 중 하나가 상속용 클래스의 생성자는 직접적으로든 간접적으로든   
재정의 가능 메소드를 호출해서는 안된다는 점입니다.    
다음은 이 규칙을 어긴 예시입니다.    

### Animal
````java
public class Animal {

    public Animal() {
        hello();
    }

    public void hello() {
        System.out.println("hello world animal");
    }
}
````

### Tiger

````java
public class Tiger extends Animal {

    private final String name;

    public Tiger() {
        name = "tiger";
    }

    @Override
    public void hello() {
        System.out.println(name);
    }

    public String getName() {
        return name;
    }
}
````

이러한 규칙을 어기면 상위 클래스의 생성자가 하위 클래스의 생성자보다 먼저 실행되므로    
하위 클래스에서 재정의한 메소드가 하위 클래스의 생성자보다 먼저 실행됩니다.    
이 문제를 해결하는 가장 좋은 방법은 상속용으로 설계하지 않은 클래스는 상속을 금지하는 것입니다.  
상속을 금지하는 방법은 2가지가 있습니다.

### Member1
````java
// 상속을 금지하는 첫번째 방법 - final 클래스
public final class Member1 {

    private String name;
    private int age;

    public Member1(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
````

### Member2
````java
// 상속을 금지하는 두번째 방법 - 생성자 private, public 정적 팩토리
public class Member2 {

    private String name;
    private int age;

    private Member2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Member2 valueOf(String name, int age) {
        return new Member2(name, age);
    }
}
````

## 정리

상속용 클래스를 설계하기란 어렵습니다. 클래스 내부에서 스스로 어떻게 사용하는지 모두 문서로 남겨야 하며    
일단 문서화한 것은 그 클래스가 쓰이는 한 반드시 지켜야 합니다. 그러지 않으면 그 내부 구현 방식을 믿고  
활용하던 하위 클래스를 오동작하게 만들 수 있습니다.   
클래스를 확장해야 할 명확한 이유가 떠오르지 않으면 상속을 금지하는 편이 나을 것입니다.   
상속을 금지하려면 클래스를 final로 선언하거나 생성자 모두를 외부에서 접근할 수 없도록 만들면 됩니다.   

[아이템 19 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item19)           
[아이템 19 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item19)    
