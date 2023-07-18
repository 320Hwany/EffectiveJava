# 아이템 50 - 적시에 방어적 복사본을 만들라

자바는 안전한 언어입니다. 자바로 작성한 클래스는 시스템의 다른 부분에서 무슨 짓을 하든  
그 불변식이 지켜집니다. 메모리 전체를 하나의 거대한 배열로 다루는 언어에서는 누릴 수 없는 강점입니다.    
하지만 아무리 자바라고 해도 다른 클래스로부터의 침범을 아무런 노력 없이 다 막을 수 있는 건 아닙니다.    
그러니 클라이언트가 여러분의 불변식을 깨뜨리려 혈안이 되어 있다고 가정하고 방어적으로 프로그래밍해야 합니다.       

## PeriodBad - 기간을 표현하는 클래스, 불변식을 지키지 못했다

````java
public final class PeriodBad {

    private final Date start;
    private final Date end;

    public PeriodBad(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }

        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
````

얼핏 이 클래스는 불변처럼 보이지만 Date가 가변이라는 사실을 이용하면 어렵지 않게   
불변식을 깨뜨릴 수 있습니다.    

````java
@Test
@DisplayName("기간을 표현하는 클래스 - 불변식을 지키지 못했다")
void test1() {
    // given
    Date start = new Date();
    Date end = new Date();
    PeriodBad p = new PeriodBad(start, end);

    // when
    Date end1 = new Date(p.end().getTime());
    end.setYear(78);
    Date end2 = new Date(p.end().getTime());

    // then
    assertThat(end1).isNotEqualTo(end2);
}
````

## PeriodBetter - 하지만 여전히 접근자 메소드가 내부의 가변 정보를 직접 드러냄

````java
public class PeriodBetter {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
    public PeriodBetter(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
````

생성자의 매개변수의 방어적 복사본을 만들었지만 여전히 접근자 메소드를 통해 내부의 가변 정보를   
직접 드러냅니다.    

## PeriodGood - 수정자 접근자, 필드의 방어적 복사본을 반환한다

````java
public class PeriodGood {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
    public PeriodGood(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    // 수정자 접근자 - 필드의 방어적 복사본을 반환한다
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
````

이제 네이티브 메소드나 리플렉션 같이 언어 외적인 수단을 동원하지 않는다면 불변을 유지할 수 있습니다.      
또한 Date는 낡은 API이기 때문에 새로운 코드를 작성할 때는 더 이상 사용하지 않고 Instant,    
LocalDateTime, ZonedDateTime을 이용해야 합니다.    

하지만 방어적 복사에는 성능 저하가 따르고 항상 사용할 수 있는 것도 아닙니다.     
되도록 불변 객체들을 조합해 객체를 구성해야 방어적 복사를 할 일이 줄어듭니다.   

## 정리

클래스가 클라이언트로부터 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면 그 요소는 반드시 방어적으로 복사해야 합니다.   
복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할 일이 없음을 신뢰한다면 방어적 복사를 수행하는 대신   
해당 구성요소를 수정했을 때의 책임이 클라이언트에 있음을 문서에 명시하도록 해야합니다.       

[아이템 50 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item50)                                                                                    
[아이템 50 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item50)         