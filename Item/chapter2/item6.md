# 아이템 6 - 불필요한 객체 생성을 피하라

똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용하는 편이 나을 때가 많습니다.   
불변 객체는 언제든 재사용할 수 있습니다.    

## StringTest

```java
public class StringTest {

    @Test
    @DisplayName("String은 리터럴 방식으로 선언해야 객체를 재사용할 수 있습니다")
    void test() {
        // bad
        String bad1 = new String("hello");
        String bad2 = new String("hello");

        // good
        String good1 = "hello";
        String good2 = "hello";

        // expected
        assertThat(bad1).isNotSameAs(bad2);
        assertThat(good1).isSameAs(good2);
    }
}
```

String은 리터럴 방식으로 선언하면 같은 가상 머신 안에서 이와 똑같은 문자열 리터럴을 사용하는  
모든 코드가 같은 객체를 사용함이 보장됩니다.   

## Regex

생성 비용이 아주 비싼 객체가 반복해서 필요하다면 캐싱하여 재사용하는 방법을 생각해볼 수 있습니다.     
MY_REGEX를 미리 생성해 캐싱해두고 메소드가 호출될 때마다 인스턴스를 재사용하면 됩니다.   

```java
public class Regex {

    private static final Pattern MY_REGEX = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,12}$");

    public static boolean isBadWay(String s) {
        return s.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,12}$");
    }

    public static boolean isGoodWay(String s) {
        return MY_REGEX.matcher(s).matches();
    }
}
```

## AutoBoxing

오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만 완전히 없애주는 것은 아닙니다.    
sumBadWay() 방식은 무수히 많은 객체를 생성합니다.   
박싱된 기본 타입보다는 기본 타입을 사용하고 의도치 않은 오토박싱이 숨어들지 않도록 주의해야 합니다.    

```java
public class AutoBoxing {

    private Long sumLong = 0L;
    private long sumlong = 0;

    public long sumBadWay() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sumLong += i;
        }
        return sumLong;
    }

    public long sumGoodWay() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sumlong += i;
        }
        return sumlong;
    }
}
```

## 정리

기존 객체를 재사용해야 한다면 새로운 객체를 만들지 않는 방법이 좋지만 요즘의 JVM에서는 최적화가 잘 되어있어    
프로그램의 명확성, 간결성, 기능을 위해서 객체를 추가로 생성하는 일은 일반적으로 좋은 일입니다.     
객체를 재사용하다가 발생하다는 프로그램의 오류는 단순히 불필요한 객체 생성으로 인해 발생하는 성능의 영향보다 훨씬 큽니다.     

[아이템 6 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item6)              
[아이템 6 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter2/item6)

