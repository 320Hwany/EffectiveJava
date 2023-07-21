# 아이템 55 - 옵셔널 반환은 신중히 하라

자바 8전에는 메소드가 특정 조건에서 값을 반환할 수 없을 때 취할 수 있는 선택지가 2가지 있었습니다.   
예외를 던지거나, null을 반환하는 것입니다. 하지만 예외를 던지는 것은 진짜 예외적인 상황에서만   
사용해야 하며 null을 반환하는 것은 별도의 null 처리 코드를 추가해야 합니다.    
자바 버전이 8로 올라가면서 Optional이라는 선택지가 생겼습니다.    
옵셔널은 원소를 최대 1개 가질 수 있는 불변 컬렉션입니다.         
옵셔널을 반환하는 메소드는 예외를 던지는 메소드보다 유연하고 사용하기 쉬우며 null을 반환하는 메소드보다    
오류 가능성이 적습니다.    

## OptionalUse

````java
public class OptionalUse {

    // 컬렉션에서 최댓값을 구한다 (컬렉션이 비었으면 예외를 던진다)
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("컬렉션이 비어있습니다");
        }

        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return result;
    }

    // 컬렉션에서 최댓값을 구해 Optional<E>로 반환한다
    public static <E extends Comparable<E>> Optional<E> maxWithOptional(Collection<E> c) {
        if (c.isEmpty()) {
            return Optional.empty();
        }

        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return Optional.of(result);
    }

    // 컬렉션에서 최댓값을 구해 Optional<E>로 반환한다 - 스트림 버전
    public static <E extends Comparable<E>> Optional<E> maxWithStream(Collection<E> c) {
        return c.stream().max(Comparator.naturalOrder());
    }

    ...
}
````

## 옵셔널 활용 1 - 기본 값을 정해둘 수 있다

````java
@Test
void test1() {
    // given
    List<Integer> integers1 = List.of(10, 20, 30);
    List<Integer> integers2 = List.of();

    // when
    Integer value1 = OptionalUse.maxWithOptional(integers1).orElse(0);
    Integer value2 = OptionalUse.maxWithOptional(integers2).orElse(-1);

    // then
    assertThat(value1).isEqualTo(30);
    assertThat(value2).isEqualTo(-1);
}
````

## 옵셔널 활용 2 - 원하는 예외를 던질 수 있다

````java
@Test
void test2() {
    // given
    List<Integer> integers1 = List.of(10, 20, 30);
    List<Integer> integers2 = List.of();

    // when
    Integer value1 = OptionalUse.maxWithOptional(integers1)
            .orElseThrow(IllegalArgumentException::new);

    // then
    assertThat(value1).isEqualTo(30);
    assertThatThrownBy(() -> throwError(integers2))
            .isInstanceOf(IllegalArgumentException.class);
}
````

또한 항상 값이 채워져 있다는게 확실하다면 get 메소드로 바로 가져올 수도 있습니다.    

## int, long, double 기본타입 전용 옵셔널 클래스가 존재한다

int, long, double 기본타입 전용 옵셔널 클래스가 존재하기 때문에 박싱된 기본 타입을 담은   
옵셔널을 반환하는 일은 없어야 합니다.     

````java
public class OptionalUse {

    ...

    public OptionalInt optionalInt(int a) {
        return OptionalInt.of(a);
    }

    public OptionalLong optionalLong(long a) {
        return OptionalLong.of(a);
    }

    public OptionalDouble optionalDouble(double a) {
        return OptionalDouble.of(a);
    }
}
````

## 정리

값을 반환하지 못할 가능성이 있고 호출할 때마다 반환값이 없을 가능성을 염두에 둬야 하는 메소드라면        
옵셔널을 반환해야할 상황일 수 있습니다. 하지만 옵셔널 반환에는 성능 저하가 뒤따르니 성능에 민감한 경우에는     
사용하지 않는 것이 좋습니다.        
또한 옵셔널은 컬렉션의 키, 값, 원소나 배열의 원소로 사용하는 게 적절한 상황은 거의 없습니다.      
옵셔널을 반환값 이외의 용도로 사용하는 것은 일반적으로 좋지 않습니다.          

[아이템 55 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item55)                                                                                          
[아이템 55 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item55)           