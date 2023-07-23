# 아이템 59 - 라이브러리를 익히고 사용하라

무작위 정수 하나를 생성한다고 할 때 직접 구현한 예제 코드를 살펴보겠습니다.       

### 라이브러리를 사용하지 않고 직접 구현 - 문제를 내포

````java
public class DirectImplementation {

    private static Random rnd = new Random();

    public static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }
}
````

위와 같은 경우 n이 그리 크지 않은 2의 제곱수라면 얼마 지나지 않아 같은 수열이 반복될 것입니다.    
또한 n이 2의 제곱수가 아니라면 몇몇 숫자가 평균적으로 더 자주 반환되며 n 값이 크면 이 현상은       
더 두드러질 것입니다.    

### random 메소드가 약 50만 개가 아니라 666666에 가까운 값을 얻는다 - 잘못 구현

그 결과 다음과 같은 테스트가 통과하게 됩니다.       

````java
@Test
void test1() {
    // given
    int n = 2 * (Integer.MAX_VALUE / 3);
    int low = 0;

    // when
    for (int i = 0; i < 1000000; i++) {
        if (DirectImplementation.random(n) < n / 2) {
            low++;
        }
    }

    // then
    assertThat(low).isGreaterThan(660000);
    assertThat(low).isLessThan(670000);
}
````

이 문제를 해결하려면 의사난수 생성기, 정수론, 2의 보수 계산 등에 대해 깊이 있는 이해가 필요합니다.   
하지만 우리가 하고자 하는 것의 핵심은 애플리케이션을 개발하는 것입니다.        

## 표준 라이브러리 사용의 장점

1. 그 코드를 작성한 전문가의 지식과 앞서 사용한 다른 프로그래머들의 경험을 활용할 수 있다.
2. 핵심적인 일과 크게 관련 없는 문제를 해결하느라 시간을 허비하지 않아도 된다.
3. 따로 노력하지 않아도 성능이 지속해서 개선된다.
4. 기능이 점점 많아진다.
5. 작성한 코드가 많은 사람에게 낯익은 코드가 된다.

### 이미 구현된 라이브러리가 존재한다면 적극 사용하자

다음 코드는 위와 같이 직접 구현하지 않고 이미 존재하는 라이브러리를 사용한 코드입니다.        

````java
@Test
void test2() {
    // given
    int n = 2 * (Integer.MAX_VALUE / 3);
    int low = 0;
    ThreadLocalRandom current = ThreadLocalRandom.current();

    // when
    for (int i = 0; i < 1000000; i++) {
        if (current.nextInt(n) < n / 2) {
            low++;
        }
    }

    // then
    assertThat(low).isGreaterThan(490000);
    assertThat(low).isLessThan(510000);
}
````

## 정리

아주 특별한 나만의 기능이 아니라면 누군가 이미 라이브러리 형태로 구현해놓았을 가능성이 큽니다.               
그런 라이브러리가 있다면 적극적으로 사용해야 합니다.      
일반적으로 라이브러리의 코드는 우리가 직접 작성한 코드보다 품질이 좋고 점차 개선될 가능성이 큽니다.       
라이브러리 코드는 개발자 각자가 작성하는 것보다 주목을 훨씬 많이 받아 코드 품질도 그만큼 높아집니다.       

[아이템 59 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item59)                                                                                                    
[아이템 59 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item59)            

