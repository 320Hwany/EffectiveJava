# 아이템 61 - 박싱된 기본 타입보다는 기본 타입을 사용하라

자바의 데이터 타입은 크게 두 가지로 나눌 수 있습니다. 바로 int, double, boolean 같은 기본타입과      
String, List 같은 참조 타입입니다. 그리고 각각의 기본 타입에는 대응하는 참조 타입이 하나씩 있으며        
이를 박싱된 기본 타입이라고 합니다.       
예를 들어 int, double, boolean에 대응하는 박싱된 기본 타입은 Integer, Double, Boolean 입니다.       

오토박싱과 오토언박싱 덕분에 두 타입을 크게 구분하지 않고 사용할 수 있지만 그렇다고 차이가 사라진 것은 아닙니다.         
기본 타입과 박싱된 기본 타입의 주된 차이는 크게 3가지가 있습니다.       

1. 기본 타입은 값만 가지고 있으나 박싱된 기본 타입은 값에 더해 식별성이란 속성을 갖는다
2. 기본 타입의 값은 언제나 유효하나 박싱된 기본 타입은 유효하지 않은 값 null을 가질 수 있다
3. 기본 타입이 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적이다

### 박싱된 기본 타입에 == 연산자를 사용하면 오류가 발생한다

````java
@Test
void test1() {
    // given
    Integer a1 = 127;
    Integer a2 = 127;

    Integer b1 = 128;
    Integer b2 = 128;

    // expected
    assertThat(a1).isSameAs(a2); // -128 ~ 127까지는 Integer 캐시 덕분에 == 비교 가능
    assertThat(b1).isNotSameAs(b2);
}
````

박싱된 기본 타입은 값에 더해 식별성이라는 속성이 있기 때문에 == 연산자를 사용하면 원하는 결과가 아닐 수 있습니다.    
이때 Integer의 경우 캐시가 작용해 -128 ~ 127까지는 식별자가 달라도 == 연산자로 값을 비교할 수 있습니다.       

### 기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다

````java
public class AutoUnboxing {

    static Integer i;

    public static void unbelievable() {
        if (i == 42) {
            System.out.println("Unbelievable");
        }
    }
}
````

````java
@Test
void test2() {
    // expected
    Assertions.assertThatThrownBy(AutoUnboxing::unbelievable)
            .isInstanceOf(NullPointerException.class);
}
````

하지만 이러한 박싱된 기본 타입을 사용해야할 경우가 있습니다.     
1. 컬렉션의 원소, 키, 값으로 사용
2. 매개변수화 타입이나 매개변수화 메소드의 타입 매개변수로 사용
3. 리플렉션을 통해 메소드를 호출할 때 사용

## 정리

기본 타입과 박싱된 기본 타입 중 하나를 선택해야 한다면 가능하면 기본 타입을 사용해야 합니다.    
기본 타입은 간단하고 빠릅니다. 오토박싱이 박싱된 기본 타입을 사용할 때의 번거로움을 줄여주지만    
그 위험까지 없애주지는 않습니다.     
두 박싱된 기본 타입을 == 연산자로 비교한다면 식별성 비교가 이뤄지기 때문에 원하는 결과가 아닐 수 있습니다.   
또한 같은 연산에서 기본 타입과 박싱된 기본 타입을 혼용하면 언박싱이 이뤄지며 언박싱 과정에서 NullPointerException이    
발생할 수 있기 때문에 주의해야 합니다.        
마지막으로 기본 타입을 박싱하는 작업은 필요 없는 객체를 생성하는 부작용을 나을 수 있다는 점도 고려해야 합니다.     

[아이템 61 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item61)                                                                                                    
[아이템 61 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item61)         