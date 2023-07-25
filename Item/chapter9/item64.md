# 아이템 64 - 객체는 인터페이스를 사용해 참조하라

아이템 51에서 매개변수 타입으로 클래스가 아니라 인터페이스를 참조하라고 했습니다.    
이 조언을 "객체는 클래스가 아닌 인터페이스로 참조해라"로까지 확장할 수 있습니다.    
적합한 인터페이스만 있다면 매개변수뿐 아니라 반환값, 변수, 필드를 전부 인터페이스 타입으로 선언해야 합니다.        
객체의 실제 클래스를 사용해야 할 상황은 오직 생성자로 생성할 때뿐입니다.    

인터페이스를 타입으로 사용하는 습관을 길러두면 프로그램이 훨씬 유연해질 수 있습니다.    
이때 주의할 점이 있는데 원래의 클래스가 인터페이스의 일반 규약 이외의 특별한 기능을 제공하며    
주변 코드가 이 기능에 기대어 동작한다면 새로운 클래스도 반드시 같은 기능을 제공해야 합니다.    

### Animal

````java
public interface Animal {

    String cry();

    String eat();
}
````

### Tiger

````java
public class Tiger implements Animal {

    @Override
    public String cry() {
        return "Tiger cry";
    }

    @Override
    public String eat() {
        return "Tiger eat";
    }

    // 구체 클래스만 제공하는 기능
    public String run() {
        return "Tiger run";
    }
}
````

### Eagle

````java
public class Eagle implements Animal {
    
    @Override
    public String cry() {
        return "Eagle cry";
    }

    @Override
    public String eat() {
        return "Eagle eat";
    }

    // 구체 클래스만 제공하는 기능
    public String fly() {
        return "Eagle fly";
    }
}
````

### 객체는 클래스가 아닌 인터페이스로 참조하라

````java
@Test
void test1() {
    // given
    Animal animal = new Eagle();

    // when
    String cry = animal.cry();
    String eat = animal.eat();

    // then
    assertThat(cry).isEqualTo("Eagle cry");
    assertThat(eat).isEqualTo("Eagle eat");
}
````

### 클래스 타입을 직접 사용하는 경우는 추가 메소드를 꼭 사용해야 하는 경우로 최소화 - 남발하면 안됨

````java

@Test
void test2() {
    // given
    Eagle animal = new Eagle();

    // when
    String cry = animal.cry();
    String eat = animal.eat();
    String fly = animal.fly(); // 구체 클래스만 제공하는 기능

    // then
    assertThat(cry).isEqualTo("Eagle cry");
    assertThat(eat).isEqualTo("Eagle eat");
    assertThat(fly).isEqualTo("Eagle fly");
}
````

적합한 인터페이스가 없다면 당연히 클래스를 참조해야 합니다.    
클래스 타입을 직접 사용하는 경우에는 이런 추가 메소드를 사용해야 하는 경우로 최소화해야 하며    
절대 남발하지 말아야 합니다.     

## 정리

객체는 클래스가 아닌 인터페이스로 참조해야 프로그램이 훨씬 더 유연해집니다.       
하지만 이때 주의할 점은 주변 코드가 이 기능에 기대어 동작하지 않는지 확인해야 합니다.    
적합한 인터페이스가 없다면 클래스로 참조하되 추가 메소드의 사용을 최소화해야 합니다.    
적합한 인터페이스가 없다면 클래스의 계층구조 중 필요한 기능을 만족하는 가장 덜 구체적인 클래스의 타입으로    
사용해야 합니다.         

[아이템 64 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item64)                                                                                                             
[아이템 64 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item64)            