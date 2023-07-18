# 아이템 21 - 인터페이스는 구현하는 쪽을 생각해 설계하라

자바 8 이전에는 기존 구현체를 깨뜨리지 않고는 인터페이스에 메소드를 추가할 방법이 없었지만   
자바 8부터 기존 인터페이스에 메소드를 추가할 수 있도록 default 메소드가 추가되었습니다.   
하지만 default 메소드는 구현 클래스에 대해 아무것도 모른 채 합의 없이 무작정 '삽입'된 것입니다.   
따라서 인터페이스에서 default 메소드의 추가가 구현 클래스에 어떤 영향을 줄 지 반드시 먼저 고려해야합니다.   

## Collection Interface

자바 8에서는 핵심 Collection Interface에 다수의 default 메소드가 추가되었습니다.   
주로 람다를 활용하기 위해서입니다. 인터페이스를 구현한 수많은 구현 클래스에 일일이 메소드를 구현하게 하지 않고   
공통적으로 적용할 수 있다는 점이 매력적으로 느껴집니다.   
하지만 default 메소드가 구현 클래스와 반드시 어우러지는 것은 아닙니다.    

예를들어 아파치의 SynchroziedCollection 클래스는 Collection 인터페이스에 정의된 removeIf default 메소드를    
재정의하고 있지 않습니다. default 메소드로 구현한 것을 그대로 물려받는 것입니다.   
이때 여러 쓰레드의 동기화 처리를 하지 못하기 때문에 한 쓰레드가 removeIf 메소드를 호출하면    
다음과 같이 ConcurrentModificationException이 발생합니다.    

### SynchronizedCollectionTest

````java
public class SynchronizedCollectionTest {

    @Test
    @DisplayName("2개의 쓰레드가 공유하는 환경에서 한 쓰레드가 removeIf를 호출하면 예외가 발생합니다 - synchronizedCollection")
    void test() {
        // given
        List<Integer> hello = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Collection<Integer> synchronizedCollection = Collections.synchronizedCollection(hello);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // then
        executorService.submit(() -> {
            assertThatThrownBy(() -> synchronizedCollection.removeIf(n -> n % 2 == 0))
                    .isInstanceOf(ConcurrentModificationException.class);
        });
    }
}
````

## 정리

자바 8에 추가된 default 메소드는 기존 인터페이스에 추가하면 편리하지만 구현 클래스에 어떤 상황을 미칠지   
고려해야합니다. 따라서 기존 인터페이스에 default 메소드로 새 메소드를 추가하는 일은 꼭 필요한 경우가 아니라면   
피해야합니다. 반면 새로운 인터페이스를 만드는 경우라면 표준적인 메소드 구현을 제공하는 아주 유용한 수단이며  
인터페이스를 더 쉽게 구현해 활용할 수 있게 합니다.   

[아이템 21 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item21)           
[아이템 21 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item21)  
