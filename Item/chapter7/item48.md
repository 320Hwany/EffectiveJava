# 아이템 48 - 스트림 병렬화는 주의해서 사용하라

## 동시성 프로그래밍

자바는 1996년부터 스레드, 동기화, wait/notify를 지원했고 자바 5부터는 동시성 컬렉션인 java.io.concurrent   
라이브러리와 실행자(Executor) 프레임워크를 지원했습니다.   
자바 7부터는 고성능 병렬 분해 프레임워크인 포크-조인(fork-join) 패키지를 추가했습니다.    
그리고 자바 8부터는 parallel 메소드만 한 번 호출하면 파이프라인을 병렬 실행할 수 있는 스트림을 지원했습니다.    
이처럼 자바로 동시성 프로그램을 작성하기가 점점 쉬워지고 있지만 올바르고 빠르게 작성하는 일은 여전히 어려운 작업입니다.    

예를들어 환경이 아무리 좋더라도 데이터 소스가 Stream.iterate거나 중간 연산으로 limit을 쓰면     
파이프라인 병렬화로는 성능 개선을 기대할 수 없습니다.    

## 스트림 병렬화가 필요한 경우

대체로 스트림의 소스가 ArrayList, HashMap, HashSet, ConcurrentHashMap의 인스턴스거나   
배열, int 범위, long 범위일 때 병렬화의 효과가 가장 좋습니다.    
이 자료구조들의 공통점은 모두 데이터를 원하는 크기로 정확하고 손쉽게 나눌 수 있다는 점과    
참조 지역성이 뛰어나다는 것입니다.   
참조 지역성은 다량의 데이터를 처리하는 벌크 연산을 병렬화할 때 아주 중요한 요소로 작용합니다.     

스트림 종단 연산 중 병렬화에 가장 적합한 것은 축소입니다.    
축소는 파이프라인에서 만들어진 모든 원소를 하나로 합치는 작업으로 Stream의 reduce 메소드 중 하나   
혹은 min, max, count, sum 같이 완성된 형태로 제공되는 메소드 중 하나를 선택해 수행합니다.    
또한 anyMatch, allMatch, noneMatch 처럼 조건에 맞으면 바로 반환되는 메소드도 병렬화에 적합합니다.     

스트림 병렬화는 오직 성능 최적화 수단임을 기억해야 합니다.    

## ParallelPi

병렬화에 적합한 예시를 하나 살펴보겠습니다.    

````
public class ParallelPi {

    static long piWithoutParallel(long n) {
        return LongStream.rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

    static long piWithParallel(long n) {
        return LongStream.rangeClosed(2, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }
}
````

````
class ParallelPiTest {

    @Test
    @DisplayName("소수 계산 스트림 파이프라인 - 병렬화 비교")
    void test1() {
        // given
        LocalDateTime a = LocalDateTime.now();
        long piWithoutParallel = ParallelPi.piWithoutParallel(10 ^ 60);
        LocalDateTime b = LocalDateTime.now();
        Duration duration1 = Duration.between(a, b);

        LocalDateTime c = LocalDateTime.now();
        long piParallel = ParallelPi.piWithParallel(10 ^ 60);
        LocalDateTime d = LocalDateTime.now();
        Duration duration2 = Duration.between(c, d);
        
        // then
        assertThat(duration1.toMillis()).isGreaterThan(duration2.toMillis());
    }
}
````

병렬화 버전이 2배이상의 시간을 단축한다는 것을 확인할 수 있습니다.     

## 정리

계산도 올바로 수행하고 성능도 빨라질 것이라는 확신이 있을 때만 스트림 파이프라인 병렬화를 사용해야 합니다.   
스트림을 잘못 병렬화하면 프로그램을 오동작하게 하거나 성능을 급격하게 떨어뜨립니다.    
병렬화하는 편이 낫다고 믿더라도 수정 후의 코드가 여전히 정확한 지 확인하고 운영 환경과 유사한 조건에서  
수행해보며 성능지표를 유심히 관찰해야 합니다.   
그래서 계산도 정확하고 성능도 좋아졌음이 확실해졌을 때 오직 그럴 때만 병렬화 버전 코드를 운영 코드에   
반영해야 합니다.        

[아이템 48 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item48)                                                                            
[아이템 48 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item48)     