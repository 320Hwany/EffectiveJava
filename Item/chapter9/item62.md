# 아이템 62 - 다른 타입이 적절하다면 문자열 사용을 피하라

문자열은 텍스트를 표현하도록 설계되었고 아주 유용하게 사용됩니다.    
하지만 문자열은 다른 값 타입을 대신하기에 적합하지 않습니다.     
네트워크, 키보드 입력으로부터 데이터를 받을 때 주로 문자열을 사용하는데 이러한 경우에는 입력받을 데이터가    
진자 문자열일 때만 그렇게 하는 것이 좋습니다. 받은 데이터가 수치형이라면 int, float, BigInteger 등   
적당한 수치 타입으로 변환해야 합니다.       
또한 문자열은 열거 타입을 대신하기에 적합하지 않으며 혼합 타입을 대신하기에도 적합하지 않습니다.    

문자열로 권한을 표현한 경우와 그렇지 않은 경우를 예시로 들어보겠습니다.      

## 잘못된 예 - 문자열을 사용해 권한을 부여하였다

````java
public class ThreadLocalBad {

    private ThreadLocalBad(){
    }

    public static void set(String key, Object value, Map<String, Object> keys) {
        keys.put(key, value);
    }

    public static Object get(String key, Map<String, Object> keys) {
        return keys.get(key);
    }
}
````

이 방식의 문제는 스레드 구분용 문자열 키가 전역 이름 공간에서 공유된다는 점입니다.    
만약 두 클라이언트가 서로 소통하지 못해 같은 키를 쓰기로 결정한다면 의도치 않게 같은 변수를 공유하게 됩니다.    
또한 악의적인 클라이언트라면 의도적으로 같은 키를 사용하여 다른 클라이언트의 값을 가져올 수도 있습니다.       

## 스레드마다 지역 변수를 가지고 타입 안전성을 확보한 권한 부여 방법

````java
public final class ThreadLocalBetter<T> {

    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T value) {
        this.key = value;
    }
}
````

이 방식은 키를 공유하지 않고 각 스레드마다 지역 변수로 가집니다.    
또한 타입 매개변수를 이용해 타입 안전성을 확보한 권한 부여 방법입니다.    

### 두 가지 방법을 사용하는 클라이언트 예시

test1에서는 "hello"라는 문자열 키 값이 노출됩니다. 전역으로 공유되기 때문입니다.      
하지만 test2에서는 어떤 키 값을 사용하는 지 알 수 없습니다.     

````java
public class ThreadLocalTest {

    @Test
    @DisplayName("잘못된 예 - 문자열을 사용해 권한을 부여, 문자열 키가 전역 이름 공간에서 공유됨")
    void test1() {
        // given
        Member member = new Member("이름", 20);
        Map<String, Object> keys = new ConcurrentHashMap<>();

        // when
        ThreadLocalBad.set("hello", member, keys);
        Member getMember = (Member) ThreadLocalBad.get("hello", keys);

        // then
        assertThat(getMember.getName()).isEqualTo("이름");
        assertThat(getMember.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("스레드마다 지역 변수를 가지고 타입 안전성을 확보한 권한 부여 방법")
    void test2() {
        // given
        Member member = new Member("이름", 20);

        // when
        ThreadLocalBetter<Member> keys = new ThreadLocalBetter<>();
        keys.setKey(member);
        Member getMember = keys.getKey();

        // then
        assertThat(getMember.getName()).isEqualTo("이름");
        assertThat(getMember.getAge()).isEqualTo(20);
    }
}
````

## 정리

더 적합한 데이터 타입이 있거나 새로 작성할 수 있다면 문자열을 사용하지 말아야 합니다.    
문자열은 잘못 사용하면 번거롭고, 덜 유연하고, 느리고, 오류 가능성도 큽니다.     
문자열을 잘못 사용하는 흔한 예로는 기본 타입, 열거 타입, 혼합 타입이 있습니다.         

[아이템 62 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item62)                                                                                                      
[아이템 62 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item62)          
