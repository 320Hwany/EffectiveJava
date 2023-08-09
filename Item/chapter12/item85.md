# 아이템 85 - 자바 직렬화의 대안을 찾으라

자바의 직렬화는 프로그래머가 어렵지 않게 분산 객체를 만들 수 있다고 생각해 매력적으로 느껴졌지만     
보이지 않는 생성자, API와 구현 사이의 모호해진 경계, 잠재적인 정확성 문제, 성능, 보안, 유지보수성 등   
그 대가 큽니다.       
직렬화의 근본적인 문제는 공격 범위가 너무 넓고 지속적으로 더 넓어져 방어하기 어렵다는 것입니다.     
자바의 역직렬화는 명백하고 현존하는 위험입니다. 신뢰할 수 없는 스트림을 역직렬화하면 원격 코드 실행, 서비스 거부 등의    
공격으로 이어질 수 있고 애플리케이션은 이런 공격에 취약해질 수 있습니다.      

이러한 문제를 해결하는 가장 좋은 방법은 자바 직렬화를 사용하지 않는 것입니다.    
객체와 바이트 시퀀스를 변환해주는 다른 매커니즘이 많이 있습니다.     
이 표현들의 공통점은 자바 직렬화보다 훨씬 간단하고 임의 객체 그래프를 자동으로 직렬화/역직렬화하지 않습니다.     
대신 속성-값 쌍의 집합으로 구성된 간단하고 구조화된 데이터 객체를 사용합니다.       
그리고 기본 타입 몇 개와 배열 타입만 지원하고 이러한 간단한 추상화만으로 아주 강력한 분산 시스템을 구축하기에 충분하고    
자바 직렬화가 가져온 심각한 문제들을 회피할 수 있습니다.      

크로스-플랫폼 구조화된 데이터 표현의 선두주자는 JSON과 프로토콜 버퍼입니다.     
둘의 가장 큰 차이는 JSON은 텍스트 기반이라 사람이 읽을 수 있고 프로토콜 버퍼는 이진 표현이라 효율이 높다는 점입니다.      

### 직렬화 말고 Json으로 만들 수 있도록 설계

객체를 JSON으로 변환할 수 있도록 Member 클래스를 만들어보았습니다.    
기본 생성자, Getter가 있어야 합니다.     

````java
public class Member {

    private String name;
    private int age;

    public Member() {
    }

    public Member(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
````

### 자바 직렬화는 부작용이 많으니 Json 방식으로 저장하는 것을 고려해야 합니다

이제 Member를 다른 분산 시스템에 저장해야 한다면 아래와 같이 ObjectMapper를 이용해 writeValueAsString로    
JSON으로 만들어 저장할 수 있습니다.     
다시 객체로 사용해야 한다면 이렇게 만들어진 JSON을 readValue를 사용해 원래 객체로 만들 수 있습니다.    

````java
@Test
void test1() throws JsonProcessingException {
    // given
    ObjectMapper objectMapper = new ObjectMapper();
    Member member = new Member("이름", 20);

    // when
    String memberJson = objectMapper.writeValueAsString(member);
    Member readValue = objectMapper.readValue(memberJson, Member.class);

    // then
    assertThat(member).isEqualTo(readValue);
}
````

## 정리

직렬화는 위험하니 피해야 합니다. 시스템을 밑바닥부터 설계한다면 JSON이나 프로토콜 버퍼 같은 대안을 사용해야 합니다.    
신뢰할 수 없는 데이터는 역직렬화하지 말아야합니다.     
꼭 해야 한다면 객체 역직렬화 필터링을 사용하되 이마저도 모든 공격을 막아줄 수는 없음을 기억해야 합니다.    
클래스가 직렬화를 지원하도록 만들지 말고 꼭 그렇게 만들어야 한다면 정말 신경써서 작성해야 합니다.     


[아이템 85 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item85)                                                                                                       
[아이템 85 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter12/item85)                 
