# 아이템 81 - wait와 notify보다는 동시성 유틸리티를 애용하라

자바 5에서 도입된 고수준의 동시성 유틸리티가 이전이라면 wait와 notify로 하드코딩해야 했던 전형적인 일들을   
대신 처리해줍니다. wait와 notify는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용해야 합니다.   
java.util.concurrent의 고수준 유틸리티는 세 범주로 나눌 수 있습니다.      
바로 실행자 프레임워크, 동시성 컬렉션, 동기화 장치입니다.      

동시성 컬렉션은 List, Queue, Map 같은 표준 컬렉션 인터페이스에 동시성을 가미해 구현한 고성능 컬렉션입니다.     
높은 동시성에 도달하기 위해 동기화를 각자의 내부에서 수행하기 때문에 동시성 컬렉션에서 동시성을 무력화하는 것은   
불가능하며 외부에서 락을 추가로 사용하면 오히려 속도가 느려집니다.          

동시성 컬렉션은 동기화한 컬렉션을 낡은 유산으로 만들었습니다. Collections.synchronizedMap 보다는    
ConcurrentHashMap을 사용하는 것이 훨씬 좋으며 동기화된 맵을 동시성 맵을 교체하는 것만으로    
동시성 애플리케이션의 성능은 극적으로 개선됩니다.

### ConcurrentMap으로 구현한 동시성 정규화 맵 - 최적은 아니다

````java
@Test
void test1() {
    // given
    Map<String, String> map = new ConcurrentHashMap<>();
    String s = "hello";

    // when
    String result1 = intern1(map, s);
    String result2 = intern1(map, s);

    // then
    assertThat(result1).isEqualTo("hello");
    assertThat(result2).isEqualTo("hello");
}

private static String intern1(Map<String, String> map, String s) {
    String previousValue = map.putIfAbsent(s, s);
    return previousValue == null ? s : previousValue;
}
````

### ConcurrentMap으로 구현한 동시성 정규화 맵 - 더 빠르다!

````java
@Test
void test2() {
    // given
    Map<String, String> map = new ConcurrentHashMap<>();
    String s = "hello";

    // when
    String result1 = intern2(map, s);
    String result2 = intern2(map, s);

    // then
    assertThat(result1).isEqualTo("hello");
    assertThat(result2).isEqualTo("hello");
}

private static String intern2(Map<String, String> map, String s) {
    String result = map.get(s);
    if (result == null) {
        result = map.putIfAbsent(s, s);
        if (result == null) {
            result = s;
        }
    }
    return result;
}
````

동기화 장치는 스레드가 다른 스레드를 기다릴 수 있게 하여 서로 작업을 조율할 수 있게 해줍니다.   
가장 자주 쓰이는 동기화 장치는 CountDownLatch와 Semaphore입니다.    

### 동시 실행 시간을 재는 간단한 프레임워크

````java
public class CountConcurrentTime {

    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                // 타이머에게 준비를 마쳤음을 알린다
                ready.countDown();
                try {
                    // 모든 작업자 스레드가 준비될 때까지 기다린다
                    start.await();
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // 타이머에게 작업을 마쳤음을 알린다
                    done.countDown();
                }
            });
        }

        ready.await(); // 모든 작업자가 준비될 때까지 기다린다
        long startNanos = System.nanoTime();
        start.countDown(); // 작업자들을 깨운다
        done.await(); // 모든 작업자가 일을 끝마치기를 기다린다
        return System.nanoTime() - startNanos;
    }
}
````

또한 시간 간격을 잴 때는 항상 System.currentTimeMillis가 아닌 System.nanoTime을 사용해야 합니다.   
System.nanoTime은 더 정확하고 정밀하며 시스템의 실시간 시계의 시간 보정에 영향을 받지 않습니다.    

## 정리

wait와 notify를 직접 사용하는 것을 동시성 '어셈블리 언어'로 프로그래밍하는 것에 비유할 수 있습니다.       
반면 java.util.concurrent는 고수준 언어에 비유할 수 있습니다.      
코드를 새로 작성한다면 wait와 notify를 쓸 이유가 거의 없습니다.     
이들을 사용하는 레거시 코드를 유지보수해야 한다면 wait는 항상 표준 관용구에 따라 while 문 안에서 호출해야 합니다.    
일반적으로 notify보다는 notifyAll을 사용해야 하며 혹시라도 notify를 사용한다면 응답 불가 상태에 빠지지 않도록   
주의해야 합니다.         

[아이템 81 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter11/item81)                                                                                                  
[아이템 81 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter11/item81)           