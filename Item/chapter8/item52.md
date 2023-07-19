# 아이템 52 - 다중정의는 신중히 사용하라

## 컬렉션 분류기 - 오류!

````java
public class CollectionClassifier {

    public static String classify(Set<?> s) {
        return "집합";
    }

    public static String classify(List<?> lst) {
        return "리스트";
    }

    public static String classify(Collection<?> c) {
        return "그 외";
    }
}
````

위 컬렉션 분류기는 오버로딩을 하였기 때문에 세 classify 중 어느 메소드를 호출할지가 컴파일타임에 정해집니다.     

### 오버로딩은 어느 메소드를 호출할지가 컴파일 타임에 정해짐

````java
@Test
void test1() {
    // given
    Set<String> sets = new HashSet<>();
    List<BigInteger> bigIntegers = new ArrayList<>();
    Map<String, String> maps = new HashMap<>();

    Collection<?>[] collections = {sets, bigIntegers, maps.values()};

    // expected
    for (Collection<?> c : collections) {
        assertThat(classify(c)).isEqualTo("그 외");
    }
}
````

오버라이딩한 메소드는 런타임시에 동적으로 선택되고 오버로딩한 메소드는 컴파일타임에 정적으로 선택됩니다.     

## 재정의된 메소드 호출 메커니즘

### Wine

````java
public class Wine {

    public String name() {
        return "포도주";
    }
}
````

### SparklingWine

````java
public class SparklingWine extends Wine {

    @Override
    public String name() {
        return "발포성 포도주";
    }
}
````

### Champagne

````java
public class Champagne extends SparklingWine {

    @Override
    public String name() {
        return "샴페인";
    }
}
````

### 컴파일타임 타입이 모두 Wine인 것과 무관하게 항상 가장 하위에서 정의한 재정의 메소드가 실행됨

````java
@Test
void test2() {
    // given
    Wine wine = new Wine();
    SparklingWine sparklingWine = new SparklingWine();
    Champagne champagne = new Champagne();

    List<Wine> wines = List.of(wine, sparklingWine, champagne);

    // when
    Wine wine1 = wines.get(0);
    Wine wine2 = wines.get(1);
    Wine wine3 = wines.get(2);

    // then
    assertThat(wine1.name()).isEqualTo("포도주");
    assertThat(wine2.name()).isEqualTo("발포성 포도주");
    assertThat(wine3.name()).isEqualTo("샴페인");
}
````

## List의 remove는 오버로딩되어 원하는대로 동작하지 않을 수 있다

````java
@Test
void test3() {
    // given
    Set<Integer> set = new TreeSet<>();
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    // when
    for (int i = -3; i < 3; i++) {
        set.add(i);
        list1.add(i);
        list2.add(i);
    }

    for (int i = 0; i < 3; i++) {
        set.remove(i);
        list1.remove(i);
        list2.remove((Integer) i);
    }
  
    // then
    assertThat(set.contains(-3)).isTrue();
    assertThat(set.contains(-2)).isTrue();
    assertThat(set.contains(-1)).isTrue();

    assertThat(list1.contains(-2)).isTrue();
    assertThat(list1.contains(0)).isTrue();
    assertThat(list1.contains(2)).isTrue();

    assertThat(list2.contains(-3)).isTrue();
    assertThat(list2.contains(-2)).isTrue();
    assertThat(list2.contains(-1)).isTrue();
}
````

## 정리

일반적으로 매개변수 수가 같을 때는 다중정의를 피하는게 좋습니다.     
안전하고 보수적으로 가려면 매개변수 수가 같은 다중정의는 만들지 말아야 합니다.         
헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메소드가 선택되도록 해야합니다.     

[아이템 52 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item52)                                                                                      
[아이템 52 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item52)       