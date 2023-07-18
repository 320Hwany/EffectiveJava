# 아이템 4 - 인스턴스화를 막으려거든 private 생성자를 사용하라

단순히 정적 메소드와 정적 필드만을 담은 클래스를 만들고 싶을 경우 클래스를 생성하고    
인스턴스를 불가능하게 만들면 됩니다.    

## Member

private 생성자를 추가하면 클래스의 인스턴스화를 막을 수 있습니다.        

```java
public class Member {

    private Member() {
        throw new IllegalArgumentException("인스턴스화 할 수 없습니다");
    }

    public static final String name = "이름";

    public static final int age = 20;
}
```

인스턴스화 방식은 상속을 불가능하게 하는 효과도 있습니다.    
모든 생성자는 명시적이든 묵시적이든 상위 클래스의 생성자를 호출하게 되는데 이를 private으로       
선언했으니 하위 클래스가 상위 클래스의 생성자에 접근할 길이 막혀버립니다.           

[아이템 4 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item4)          
[아이템 4 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter2/item4)      

