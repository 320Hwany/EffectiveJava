# 아이템 44 - 표준 함수형 인터페이스를 사용하라

자바가 람다를 지원하면서 API를 작성하는 모범 사례도 크게 바뀌었습니다.   
예를들어 상위 클래스의 기본 메소드를 재정의해 원하는 동작을 구현하는 템플릿 메소드 패턴의 매력이 크게 줄었습니다.    
이를 대체하는 현대적인 해법은 같은 효과의 함수 객체를 받는 정적 팩토리나 생성자를 제공하는 것입니다.    
즉 함수 객체를 매개 변수로 받는 생성자와 메소드를 더 많이 만들어야 한다는 것입니다.    

````java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>() {

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > 3;
        }
    };
````

위와 같이 LinkedHashMap의 removeEldestEntry 메소드를 재정의하여 캐시로 사용할 수 있습니다.   
잘 동작은 하지만 람다를 사용하면 훨씬 나을 것입니다. LinkedHashMap을 오늘날 다시 구현한다면   
함수 객체를 받는 정적 팩토리나 생성자를 제공했을 것입니다.    
LinkedHashMap을 다시 구현한다고 생각하고 CustomLinkedHashMap을 만들어보았습니다.     

## CustomLinkedHashMap - LinkedHashMap을 상속 받아 함수 객체를 받는 생성자를 제공한 Custom 클래스

````java
public class CustomLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final BiPredicate<Map<K, V>, Map.Entry<K, V>> removalFunction;

    public CustomLinkedHashMap(BiPredicate<Map<K, V>, Map.Entry<K, V>> removalFunction) {
        this.removalFunction = removalFunction;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return removalFunction.test(this, eldest);
    }

    // 예시로 사용해보기 위한 메소드
    public static <K, V> CustomLinkedHashMap<K, V> createCache(BiPredicate<Map<K, V>,
            Map.Entry<K, V>> removalFunction) {
        return new CustomLinkedHashMap<>(removalFunction);
    }
}
````

````java
@Test
@DisplayName("CustomLinkedHashMap는 LinkedHashMap을 상속 받아 함수 객체를 받는 생성자를 제공한 Custom 클래스")
void test3() {
    // given
    BiPredicate<Map<String, Integer>, Map.Entry<String, Integer>> removalFunction = (m, eldest) -> m.size() > 3;

    // when
    CustomLinkedHashMap<String, Integer> map = CustomLinkedHashMap.createCache(removalFunction);

    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("d", 4);
    map.put("e", 5);

    // then
    assertThat(map.size()).isEqualTo(3);
    assertThat(map.get("c")).isEqualTo(3);
    assertThat(map.get("d")).isEqualTo(4);
    assertThat(map.get("e")).isEqualTo(5);
}
````

람다를 이용하여 수행하고자 하는 동작을 정의하고 이것을 메소드가 함수 객체를 받아 수행하는 것을 볼 수 있습니다.      

## 표준 함수형 인터페이스

자바에서 표준 함수형 인터페이스는 총 43개입니다. 위에서 사용한 BiPredicate도 그것중 하나입니다.     
함수형 인터페이스를 직접 정의해서 사용해도 되지만 이미 많은 부분을 자바 진영에서 구현했기 때문에       
필요한 용도에 맞는게 있다면 직접 구현하지 않고 표준 함수형 인터페이스를 사용하는 것이 더 좋은 방법입니다.       

대표적인 6가지의 사례를 확인해보겠습니다.   

반환 값과 인수의 타입이 같은 UnaryOperator, BinaryOperator             
인수 하나를 받고 boolean을 반환하는 Predicate 인수와 반환 타입이 다른 Function      
인수를 받지 않고 값을 반환하는 Supplier, 인수 하나를 받고 반환 값이 없는 Consumer가 있습니다.      
````java
@Test
@DisplayName("UnaryOperator를 사용하여 반환값과 인수의 타입이 같은 함수를 만든다")
void test5() {
    // given
    UnaryOperator<String> toLowerCaseOperator = String::toLowerCase;

    // when
    String hello = toLowerCaseOperator.apply("HELLO");

    // then
    assertThat(hello).isEqualTo("hello");
}

@Test
@DisplayName("BinaryOperator 사용하여 반환값과 인수의 타입이 같은 함수를 만든다")
void test6() {
    // given
    BinaryOperator<BigInteger> binaryOperator = BigInteger::add;

    // when
    BigInteger num1 = new BigInteger("10");
    BigInteger num2 = new BigInteger("20");
    BigInteger result = binaryOperator.apply(num1, num2);

    // then
    assertThat(result).isEqualTo(30);
}

@Test
@DisplayName("Predicate를 사용하여 인수하나를 받아 boolean 값을 반환합니다")
void test7() {
    // given
    Predicate<Collection<?>> isEmptyPredicate = Collection::isEmpty;
    Collection<String> collection = List.of();

    // when
    boolean result = isEmptyPredicate.test(collection);

    // then
    assertThat(result).isTrue();
}

@Test
@DisplayName("Function을 사용하여 인수와 반환 타입이 다른 함수를 만듭니다")
void test8() {
    // given
    Function<Object[], List<Object>> asListFunction = Arrays::asList;

    Object[] array = {1, 2, 3, 4, 5};

    // when
    List<Object> list = asListFunction.apply(array);

    // then
    assertThat(list.size()).isEqualTo(5);
    assertThat(list.get(0)).isEqualTo(1);
    assertThat(list.get(1)).isEqualTo(2);
    assertThat(list.get(2)).isEqualTo(3);
    assertThat(list.get(3)).isEqualTo(4);
    assertThat(list.get(4)).isEqualTo(5);
}

@Test
@DisplayName("Supplier를 사용하여 인수를 받지 않고 값을 반환하는 함수를 만듭니다")
void test9() {
    // given
    Supplier<Instant> nowSupplier = Instant::now;

    // when
    Instant currentInstant = nowSupplier.get();

    // then
    assertThat(currentInstant).isNotNull();
}

@Test
@DisplayName("Consumer를 사용하여 인수를 하나 받고 반환 값이 없는 함수를 만듭니다")
void test10() {
    // given
    Consumer<String> consumer = System.out::println;

    // when && then
    consumer.accept("hello world");
}
````

위의 기본 6개를 바탕으로 매개변수 타입, 개수가 다름에 따라 변형하여 총 43개가 있습니다.   
여기에 해당한다면 이 표준 인터페이스를 사용하는 것이 좋지만 예외가 있습니다.   
Compartor와 같이 자주 쓰이며 이름 자체가 용도를 명확히 설명해주거나 반드시 따라야 하는 규약이 있거나   
유용한 디폴트 메소드를 제공하는 경우에는 표준 인터페이스 사용보다는 전용 함수형 인터페이스를 작성하는 것도  
좋은 방법입니다.      

전용 함수형 인터페이스를 작성하기로 했다면 @FunctionalInterface 애노테이션을 달아야 합니다.  
이렇게 함으로써 인터페이스가 람다용으로 설계된 것임을 알려줄 수 있고 추상 메소드가 오직 하나일 때만 컴파일되게  
해줍니다. 따라서 직접 만든 함수형 인터페이스에는 항상 @FuncionalInterface 애노테이션을 사용해야 합니다.      

## 정리

람다를 사용하여 입력값과 반환값에 함수형 인터페이스 타입을 활용할 수 있습니다.    
일반적으로 표준 함수형 인터페이스를 사용하는 것이 가장 좋은 선택입니다.   
흔하지는 않지만 때에 따라서 직접 새로운 함수형 인터페이스를 만들어 사용할 수도 있습니다.     

[아이템 44 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item44)                                                                        
[아이템 44 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item44)       