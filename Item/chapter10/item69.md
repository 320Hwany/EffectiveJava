# 아이템 69 - 예외는 진짜 예외 상황에만 사용하라

### 예외를 완전히 잘못 사용한 예 - 따라 하지 말 것!

````java
@Test
void test1() {
    // given
    Mountain[] range = new Mountain[]{
        new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain()
    };

    int i = 0;

    // when
    try {
        while (true) {
            range[i++].climb();
        }
    } catch (ArrayIndexOutOfBoundsException e) {
    }

    assertThat(i).isEqualTo(6);
}
````

위와 같은 코드는 전혀 직관적이지 않습니다. 이 코드는 배열의 원소를 순회하는데 무한루프를 돌다가    
배열의 끝에 도달해 ArrayIndexOutOfBoundsException이 발생하면 끝을 냅니다.    

### 예외를 사용하지 않고 올바르게 수정한 예시

````java
@Test
void test2() {
    // given
    Mountain[] range = new Mountain[]{
            new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain()
    };

    int i = 0;

    // when
    for (Mountain m : range) {
        i++;
        m.climb();
    }

    // then
    assertThat(i).isEqualTo(5);
}
````

예외는 오직 예외 상황에서만 사용해야 합니다. 절대로 일상적인 제어 흐름용으로 쓰여선 안됩니다.     
잘 설계된 API라면 클라이언트가 정상적인 제어 흐름에서 예외를 사용할 일이 없게 해야 합니다.      

특정 상태에서만 호출할 수 있는 '상태 의존적' 메소드를 제공하는 클래스는 '상태 검사' 메소드도 함께     
제공해야 합니다. Iterator 인터페이스의 next와 hasNext가 각각 상태 의존적 메소드와 상태 검사 메소드에 해당합니다.    

### 상태 의존적 메소드를 상태 검사 메소드와 함께 제공

````java
@Test
void test3() {
    // given
    List<Mountain> mountains =
            List.of(new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain());
    int k = 0;

    // when
    for (Iterator<Mountain> i = mountains.iterator(); i.hasNext(); ) {
        k++;
        Mountain mountain = i.next();
        mountain.climb();
    }

    // then
    assertThat(k).isEqualTo(5);
}
````

### 상태 검사 메소드를 사용하지 않는다면 클라이언트가 직접 해야함 - 하지 말 것

````java
@Test
void test4() {
    // given
    List<Mountain> mountains =
            List.of(new Mountain(), new Mountain(), new Mountain(), new Mountain(), new Mountain());
    int k = 0;

    try {
        Iterator<Mountain> i = mountains.iterator();
        while (true) {
            k++;
            Mountain mountain = i.next();
            mountain.climb();
        }
    } catch (NoSuchElementException e) {
    }

    // then
    assertThat(k).isEqualTo(6);
}
````

상태 검사 메소드 대신 올바르지 않은 상태일 때 빈 옵셔널을 반환하는 방법도 있습니다.       

## 정리

예외는 예외 상황에서 쓸 의도로 설계되었습니다. 정상적인 제어 흐름에서 사용해서는 안되며    
이를 프로그래머에게 강요하는 API를 만들어서도 안됩니다.        

[아이템 69 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item69)                                                                                          
[아이템 69 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter10/item69)     