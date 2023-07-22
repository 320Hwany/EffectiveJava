# 아이템 58 - 전통적인 for 문보다는 for-each 문을 사용하라

전통적인 for 문은 while 문보다는 낫지만 가장 좋은 방법은 아닙니다.            
반복자와 인덱스 변수는 모두 코드를 지저분하게 할 뿐 우리에게 진짜 필요한 건 원소들뿐입니다.             
더군다나 이처럼 쓰이는 요소 종류가 늘어나면 오류가 생길 가능성이 높아집니다.       
혹시라도 잘못된 변수를 사용했을 때 컴파일러가 잡아주리라는 보장도 없습니다.     
마지막으로 컬렉션이나 배열이냐에 따라 코드 형태가 달라지는 것도 주의할 점입니다.    

### 문제는 없지만 반복자를 사용해야 해서 보기 좋진 않다 - 전통적인 for 문

````java
@Test
void test1() {
    // given
    Collection<Suit> suits = List.of(Suit.values());
    Collection<Rank> ranks = List.of(Rank.values());

    // when
    List<Card> deck = new ArrayList<>();

    // then
    for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
        Suit suit = i.next();
        for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
            deck.add(new Card(suit, j.next()));
        }
    }
    assertThat(deck.size()).isEqualTo(4 * 13);
}
````

### 컬렉션이나 배열의 중첩 반복을 위한 권장 관용구 - for-each 문

````java
@Test
void test2() {
    // given
    Collection<Suit> suits = List.of(Suit.values());
    Collection<Rank> ranks = List.of(Rank.values());

    // when
    List<Card> deck = new ArrayList<>();

    // then
    for (Suit suit : suits) {
        for (Rank rank : ranks) {
            deck.add(new Card(suit, rank));
        }
    }
    assertThat(deck.size()).isEqualTo(4 * 13);
}
````

항상 for-each 문을 사용할 수 있는 것은 아닙니다. 예를들어 다음과 같은 경우가 있습니다.    

1. 컬렉션을 순회하면서 선택된 원소를 제거하는 경우
2. 리스트나 배열을 순회하면서 그 원스의 값 일부 혹은 전체를 교체해야 한다면 인덱스를 사용해야 함
3. 여러 컬렉션을 병렬로 순회해야 한다면 반복자와 인덱스 변수를 사용해 제어해야 함

3가지 상황 중 하나에 속할 때는 일반적인 for 문을 사용하되 위에서 언급한 문제들에 주의해야 합니다.    

## 정리

전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방 해줍니다.          
성능 저하도 없습니다. 가능한 모든 곳에서는 for 문이 아닌 for-each 문을 사용해야 합니다.      

[아이템 58 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item58)                                                                                                   
[아이템 58 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item58)           

