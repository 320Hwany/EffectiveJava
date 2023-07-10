# 아이템 40 - @Override 애노테이션을 일관되게 사용하라

자바가 기본으로 제공하는 애노테이션 중 가장 중요한 것중 하나는 @Override 입니다.   
@Override는 메소드 선언에만 달 수 있으면 이 애노테이션이 달렸다는 것은 상위 타입의 메소드를   
재정의했다는 것을 말합니다.    
이 애노테이션을 일관되게 사용하면 여러 버그를 예방할 수 있습니다.    

## BigramBad

````
public class BigramBad {

    private final char first;
    private final char second;

    public BigramBad(char first, char second) {
        this.first = first;
        this.second = second;
    }

    // Overriding이 아니라 Overloading을 해도 @Override를 사용하지 않으면 컴파일 에러가 발생하지 않는다
    public boolean equals(BigramBad b) {
        return b.first == first && b.second == second;
    }

    public int hashCode() {
        return 31 * first + second;
    }
}
````

equals를 오버라이딩한 것 같지만 매개변수를 보면 Object가 아니라 BigramBad 이므로 오버로딩한 것입니다.   
하지만 이경우에 컴파일러는 에러를 발생시키지 않습니다. 오버로딩을 한 것이라고 생각하는 것입니다.    

````
@Test
@DisplayName("Overriding이 아니라 Overloading을 해도 @Override를 사용하지 않으면 컴파일 에러가 발생하지 않는다")
void test1() {
    // given
    Set<BigramBad> s = new HashSet<>();

    // when
    for (int i = 0; i < 10; i++) {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            s.add(new BigramBad(ch, ch));
        }
    }

    // then
    assertThat(s.size()).isEqualTo(260);
}
````

위 테스트에서 Set 안에 260개의 원소가 있다는 테스트가 통과하는데 오버라이딩이 제대로 적용되어   
equals, hashCode가 잘 적용되었다면 Set 인터페이스에 중복을 제거한 26개의 원소만 있을 것 입니다.     

## BigramGood - equals, hashCode 재정의시 @Override 를 사용함

````
public class BigramGood {

    private final char first;
    private final char second;

    public BigramGood(char first, char second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigramGood that = (BigramGood) o;
        return first == that.first && second == that.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
````

이제 오버라이딩으로 equals, hashCode 메소드를 재정의 하였습니다.   

````
@Test
@DisplayName("@Override를 사용하면 Overriding을 하지 않았을 때 컴파일 에러를 발생시킬 수 있습니다")
void test2() {
    // given
    Set<BigramGood> s = new HashSet<>();

    // when
    for (int i = 0; i < 10; i++) {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            s.add(new BigramGood(ch, ch));
        }
    }

    // then
    assertThat(s.size()).isEqualTo(26);
}
````

Set 안에 26개의 원소가 있다는 테스트가 통과합니다.    
equals, hashCode 메소드가 잘 정의되어 중복 제거를 할 수 있습니다.     

## 정리

재정의한 모든 메소드에 @Override 애노테이션을 의식적으로 달면 실수했을 때 컴파일러가 바로 알려줄 수 있습니다.      
구체 클래스에서 상위 클래스의 추상 메소드를 재정의한 경우에는 이 애노테이션을 달지 않아도 되지만   
단다고 해서 나쁠 건 없습니다.      

[아이템 40 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item40)                                                          
[아이템 40 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item40)         