# 아이템 60 - 정확한 답이 필요하다면 float와 double은 피하라

float와 double 타입은 과학과 공학 계산용으로 설계되었습니다.    
이진 부동소수점 연산에 쓰이며 넓은 범위의 수를 빠르게 정밀한 '근사치'로 계산하도록 세심하게 설계되었습니다.        
따라서 정확한 결과가 필요할 때는 사용하면 안됩니다.      
float와 double 타입은 특히 금융 관련 계산과 맞지 않습니다.        

### 금융 계산에 부동 소수 타입을 사용하면 오류가 발생할 수 있다

````java

@Test
void test1() {
    // given
    double funds = 1.00;
    int itemsBought = 0;

    // when
    for (double price = 0.10; funds >= price; price += 0.10) {
        funds -= price;
        itemsBought++;
    }

    // then
    assertThat(itemsBought).isEqualTo(3);
    assertThat(funds).isNotEqualTo(4);
}
````

위 예시에서 3개를 구매한 후 잔돈은 4달러가 아니라 0.3999999999999999 달러이다.         
이 문제를 해결하기 위해서 금융 계산에서는 BigDecimal, int 혹은 long을 사용해야 합니다.           

### BigDecimal을 사용한 햅법 - 속도가 느리고 쓰기 불편하다

````java
@Test
void test2() {
    // given
    BigDecimal TEN_CENTS = new BigDecimal("0.10");
    int itemsBought = 0;
    BigDecimal funds = new BigDecimal("1.00");

    // when
    for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
        funds = funds.subtract(price);
        itemsBought++;
    }

    // then
    assertThat(itemsBought).isEqualTo(4);
    assertThat(funds.doubleValue()).isEqualTo(0.0);
}
````

하지만 BigDecimal은 기본 타입보다 쓰기가 훨씬 불편하고 훨씬 느립니다.        
BigDecimal의 대안으로는 int 혹은 long 타입을 사용할 수 있습니다.    
이럴 경우에는 다룰 수 있는 값의 크기가 제한되고 소수점을 직접 관리해야 합니다.      
다음 예시는 계산을 달러 대신 센트로 수행하였습니다.        

### 정수타입을 사용한 해법

````java
@Test
void test3() {
    // given
    int itemsBought = 0;
    int funds = 100;

    // when
    for (int price = 10; funds >= price; price += 10) {
        funds -= price;
        itemsBought++;
    }

    // then
    assertThat(itemsBought).isEqualTo(4);
    assertThat(funds).isEqualTo(0);
}
````

## 정리

정확한 답이 필요한 계산에는 float나 double을 피해야 합니다.    
소수점 추적은 시스템에 맡기고 코딩 시의 불편함이나 성능 저하에 신경 쓰지 않겠다면 BigDecimal을 사용할 수 있습니다.       
BigDecimal이 제공하는 여덟 가지 반올림 모드를 이용하여 반올림을 완벽히 제어할 수 있습니다.     
반면 성능이 중요하고 소수점을 직접 추적할 수 있고 숫자가 너무 크지 않다면 int나 long을 사용할 수 있습니다.      
숫자를 아홉 자리 십진수로 표현할 수 있다면 int를 사용하고 열여덟 자리 십진수로 표현할 수 있다면 long을 사용할 수 있습니다.     
열여덟 자리가 넘어가면 BigDecimal을 사용해야 합니다.       

[아이템 60 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item60)           