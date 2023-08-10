# 아이템 86 - Serializable을 구현할지는 신중히 결정하라

Serializable을 구현하면 릴리스한 뒤에는 수정하기 어렵습니다.     
클래스가 Serializable을 구현하면 직렬화된 바이트 스트림 인코딩(직렬화 형태)도 하나의 공개 API가 됩니다.     
그래서 이 클래스가 널리 퍼진다면 그 직렬화 형태도 영원히 지원해야 하는 것입니다.      
기본 직렬화 형태에서는 클래스의 private, package-private 인스턴스 필드들마저 API로 공개되는 꼴이 됩니다.     
필드로의 접근을 최대한 막아 정보를 은닉하라는 조언도 무력화됩니다.     

Serializable의 두 번째 문제는 버그와 보안 구멍이 생길 위험이 높아진다는 점입니다.    
역직렬화는 일반 생성자의 문제가 그대로 적용되는 '숨은 생성자'입니다.     

Serializable의 세 번째 문제는 해당 클래스의 신버전을 릴리스할 때 테스트할 것이 늘어난다는 점입니다.    
직렬화 가능 클래스가 수정되면신버전 인스턴스를 직렬화한 후 구버전으로 역직렬화할 수 있는지 그리고 그 반대도 가능한지를    
검사해야 합니다. 따라서 테스트해야 할 양이 직렬화 가능 클래스의 수와 릴리스 횟수에 비례해 증가합니다.      

Serializable 구현 여부는 가볍게 결정할 사안이 아니며 상속용으로 설계된 클래스는 대부분 Serializable을 구현하면   
안되며 인터페이스도 대부분 Serializable을 확장해서는 안됩니다.     

## Serializable를 이용한 직렬화 역직렬화 학습 테스트

````java
public class Member implements Serializable {

    private String name;

    private int age;

    public Member(final String name, final int age) {
        this.name = name;
        this.age = age;
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

````java
class MemberTest {

    @Test
    @DisplayName("Serializable을 구현한 Member를 직렬화 후 역직렬화하여 기존 객체와 값이 같은지 확인합니다")
    void test1() {
        // given
        Member member = new Member("name", 20);

        // when
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(member);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // then
        Member deserializedMember = deserialize(byteArrayOutputStream);
        assertThat(deserializedMember).isEqualTo(member);
    }

    private static Member deserialize(final ByteArrayOutputStream byteArrayOutputStream) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
            return (Member) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
````

## 정리

Serializable은 구현한다고 선언하기는 아주 쉽지만 그것은 눈속임일 뿐입니다.         
한 클래스의 여러 버전이 상호작용할 일이 없고 서버가 신뢰할 수 없는 데이터에 노출될 가능성이 없는 등      
보호된 환경에서만 쓰일 클래스가 아니라면 Serializable 구현은 아주 신중하게 이뤄져야 합니다.      
상속할 수 있는 클래스라면 주의사항이 더욱 많아집니다.

[아이템 86 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item86)                                                                                                           
[아이템 86 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter12/item86)        