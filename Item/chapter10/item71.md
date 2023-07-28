# 아이템 71 - 필요 없는 검사 예외 사용은 피하라

어떤 메소드가 검사 예외를 던질 수 있다고 선언됐다면, 이를 호출하는 코드에서는 catch 블록을 두어    
그 예외를 붙잡아 처리하거나 더 바깥으로 던져 문제를 전파해야만 합니다.    
어느쪽이든 API 사용자에게 부담을 줍니다.    
또한 검사 예외를 던지는 메소드는 스트림 안에서 직접 사용할 수 없기 때문에 자바 8부터는 부담이 더 커졌습니다.    

API를 제대로 사용해도 발생할 수 있는 예외이거나 프로그래머가 의미 있는 조치를 취할 수 있는 경우라면    
이 정도 부담쯤은 받아들일 수 있는 경우도 있습니다. 하지만 이러한 경우가 아니라면 비검사 예외를 사용하는 편이 좋습니다.      

## Member

````java
public class Member {

    private String name;

    private int age;

    private boolean lotto = false;

    public Member(String name, int age, boolean lotto) {
        this.name = name;
        this.age = age;
        this.lotto = lotto;
    }

    // 검사 예외를 사용
    public void win1() throws Exception {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new Exception();
        }
    }

    // 비검사 예외를 사용
    public void win2() {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new RuntimeException();
        }
    }

    // 상태 검사 메소드
    public boolean check() {
        return lotto;
    }
}
````

이제 Member를 사용하는 2가지 코드를 살펴보겠습니다.       

### 검사 예외를 던지는 메소드 - 리팩토링 전

````java
@Test
void test1() {
    // given
    Member member = new Member("이름", 20, false);
    boolean dealWith = false;

    // when
    try {
        member.win1();
    } catch (Exception e) {
        dealWith = true;
    }

    // then
    assertThat(dealWith).isTrue();
}
````

win1() 메소드가 검사 예외를 던지기 때문에 try catch를 이용해 처리했습니다.        
검사 예외를 던지는 메소드를 옵셔널을 반환하도록 하거나 상태 검사 메소드, 비검사 예외를 던지도록 하여      
리팩토링 할 수 있습니다.         

### 상태 검사 메소드와 비검사 예외를 던지는 메소드 - 리팩토링 후

````java
@Test
void test2() {
    // given
    Member member = new Member("이름", 20, false);

    // when
    if (member.check()) {
        member.win2();
    } else {
        assertThatThrownBy(() -> member.win2())
                .isInstanceOf(RuntimeException.class);
    }
}
````

상태 검사 메소드 check()를 이용하여 try catch 없이 조건에 만족하지 않을 경우    
비검사 예외가 발생하게 리팩토링할 수 있습니다.        
상태 검사 메소드를 사용할 때 여러 쓰레드가 동시에 접근할 수 있거나 외부 요인에 의해 상태가 변할 수 있는 상황   
즉, check()의 호출 시점과 win2()의 호출 시점사이에 상태가 변할 수 있다면 사용하면 안됩니다.     


## 정리

꼭 필요한 곳에만 사용한다면 검사 예외는 프로그램의 안전성을 높여주지만, 남용하면 쓰기 고통스러운 API를 만듭니다.     
API 호출자가 예외 상황에서 복구할 방법이 없다면 비검사 예외를 던져야 합니다.      
복구가 가능하고 호출자가 그 처리를 해주길 바란다면 우선 옵셔널을 반환해도 될지 고민해보고   
옵셔널만으로는 상황을 처리하기에 충분한 정보를 제공할 수 없을 때만 검사 예외를 던져야 합니다.     

[아이템 71 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter10/item71)                                                                                              
[아이템 71 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter10/item71)          