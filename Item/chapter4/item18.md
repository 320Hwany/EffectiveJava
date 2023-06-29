# 아이템 18 - 상속보다는 컴포지션을 사용하라

상속은 코드를 재사용하는 강력한 수단이지만 항상 최선은 아닙니다.    
잘못 사용하면 오류를 내기 쉬운 소프트웨어를 만들게 됩니다.

구체적인 예로 HashSet을 상속하여 InstrumentedHashSet을 만들어보겠습니다.   

### InstrumentedHashSet

````
public class InstrumentedHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
````   

### InstrumentedHashSetTest

````
@Test
@DisplayName("addAll() 메소드 실행 후 3을 반환하리라 기대하지만 6을 반환합니다 - HashSet 상속")
void test() {
    // given
    InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
    
    // when
    s.addAll(List.of("1", "2", "3");
    
    // 3이 아니라 6을 반환한다
    assertThat(s.getAddCount()).isEqualTo(6);
}
````

HashSet의 addAll 메소드가 add 메소드를 사용하여 구현했기 때문에 하위 클래스에서 재정의하면서     
getAddCount가 중복으로 계산되어 3이 아니라 6을 반환합니다.      

이렇게 상속을 사용하면 하위 클래스에서 재정의하면서 원하는 방식으로 동작하지 않을 수 있습니다.   
또한 상위 클래스를 수정하면 모든 하위 클래스에 영향을 줍니다.      

## 컴포지션

따라서 이러한 상속 방식으로 기존 클래스를 확장하는 대신 새로운 클래스를 만들고 private 필드로   
기존 클래스의 인스턴스를 참조하게 하는 방법을 고려해볼 수 있습니다.    
예를들어 Animal, Tiger 클래스를 정의할 때 상속을 사용하지 않고 컴포지션을 사용해보겠습니다.    

### Animal
````
public class Animal {

    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
````

### Tiger
````
public class Tiger {

    private Animal animal;

    public Tiger(Animal animal) {
        this.animal = animal;
    }

    public void cry() {
        System.out.println("Tiger crying");
    }

    public Animal getAnimal() {
        return animal;
    }
}
````

이러한 방식을 전달(forwarding)이라 하며 새 클래스의 메소드들을 전달 메소드(forwarding method)라고 부릅니다.   
이 방식은 새로운 클래스는 기존 클래스의 내부 구현 방식의 영향에서 벗어나며 심지어 기존 클래스에 새로운 메소드가   
추가되더라도 전혀 영향을 받지 않습니다.     

## 정리

상속은 강력하지만 캡슐화를 해친다는 문제가 있습니다. 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만  
사용해야 합니다. 심지어 is-a 관계일 때도 하위 클래스의 패키지가 상위 클래스와 다르고, 상위 클래스가 확장을 고려해    
설계되지 않았다면 여전히 문제가 있을 수 있습니다.    
따라서 상속의 취약점을 피하려면 상속보다는 컴포지션과 전달을 사용해야 합니다.   
특히 래퍼 클래스로 구현할 적당한 인터페이스가 존재한다면 컴포지션을 적극 활용해야 합니다.     

[아이템 18 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item18)         
[아이템 18 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item18)   