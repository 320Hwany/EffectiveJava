# 익명 클래스보다는 람다를 사용하라

예전에는 자바에서 함수 타입을 표현할 때 추상 메소드를 하나만 담은 인터페이스(드물게는 추상 클래스)를 사용했습니다.      
이런 인터페이스의 인스턴스를 함수 객체라고 하며 특정 함수나 동작을 나타내는데 사용했습니다.   
JDK 1.1 부터는 함수 객체를 만드는 주요 수단은 익명 클래스가 되었습니다.    

## 익명 클래스의 인스턴스를 함수 객체로 사용 - 낡은 기법

````
@Test
@DisplayName("익명 클래스의 인스턴스를 함수 객체로 사용 - 낡은 기법")
void test1() {
    // given
    List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

    // when
    Collections.sort(words, new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
        }
    });

    // then
    assertThat(words.get(0)).isEqualTo("hi");
    assertThat(words.get(1)).isEqualTo("hello");
    assertThat(words.get(2)).isEqualTo("world");
}
````

이 코드에서 Comparator 인터페이스가 정렬을 담당하는 추상 전략을 뜻하며 문자열을 정렬하는 구체적인 전략은   
익명 클래스로 구현했습니다. 하지만 이 방식은 코드가 너무 길기 때문에 자바는 함수형 프로그래밍에 적합하지 않았습니다.    

## 람다식을 함수 객체로 사용 - 익명 클래스 대체

자바 8에 와서 추상 메소드 하나짜리 인터페이스는 특별한 의미를 인정받게 되는데    
지금은 함수형 인터페이스라 부르는 이 인터페이스들의 인스턴스를 람다식을 사용해 만들 수 있게된 것입니다.    

````
@Test
void test2() {
    // given
    List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

    // when
    Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

    // then
    assertThat(words.get(0)).isEqualTo("hi");
    assertThat(words.get(1)).isEqualTo("hello");
    assertThat(words.get(2)).isEqualTo("world");
}
````

타입을 명시해야 코드가 명확할 때만 제외하고는 람다의 모든 매개변수 타입은 생략합니다.         
컴파일러가 타입을 추론하는데 필요한 타입 정보는 대부분 제네릭을 통해 얻을 수 있습니다.       

## 람다자리에 비교자 생성 메소드를 사용하여 더 간결하게 표현

````
@Test
void test3() {
    // given
    List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

    // when
    Collections.sort(words, Comparator.comparingInt(String::length));

    // then
    assertThat(words.get(0)).isEqualTo("hi");
    assertThat(words.get(1)).isEqualTo("hello");
    assertThat(words.get(2)).isEqualTo("world");
}
````

## 자바 8때 List 인터페이스에 추가된 sort 메소드를 이용해 더 간결하게 표현

````
@Test
void test4() {
    // given
    List<String> words = new ArrayList<>(List.of("hello", "world", "hi"));

    // when
    words.sort(Comparator.comparingInt(String::length));

    // then
    assertThat(words.get(0)).isEqualTo("hi");
    assertThat(words.get(1)).isEqualTo("hello");
    assertThat(words.get(2)).isEqualTo("world");
}
````

## 열거 타입에 인스턴스 필드를 두는 방식

단순히 각 열거 타입 상수의 동작을 람다로 구현해 생성자에 넘기고 생성자는 이 람다를 인스턴스 필드로 저장해둡니다.       
````
public enum Operation {

    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y){
        return op.applyAsDouble(x, y);
    }
}
````

하지만 람다가 길거나 읽기 어렵다면 더 간단히 줄여보거나 람다를 쓰지 않는 쪽이 더 낫습니다.       
그리고 람다는 자신을 참조할 수 없습니다. 람다에서의 this 키워드는 바깥 인스턴스를 가리킵니다.   
반면 익명 클래스에서의 this는 익명 클래스의 인스턴스 자신을 가리킵니다.   

## 정리

자바 8버전부터 작은 함수 객체를 구현하는 데 적합한 람다가 도입되었습니다.    
익명 클래스는 함수형 인터페이스가 아닌 타입의 인스턴스를 만들 때만 사용해야 합니다.   
람다는 작은 함수 객체를 아주 쉽게 표현할 수 있어 함수형 프로그래밍의 지평을 열었습니다.        

[아이템 42 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item42)                                                                       
[아이템 42 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item42)      