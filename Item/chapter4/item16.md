# 아이템 16 - public 클래스에서는 public 필드가 아닌 접근자 메소드를 사용하라

public 클래스에서 public 필드로 작성하면 데이터 필드에 직접 접근할 수 있어 캡슐화의 이점을 제공하지 못합니다.      
API를 수정하지 않고는 내부 표현을 바꿀 수 없고, 불변식을 보장할 수 없으며, 외부에서 필드에 접근할 때 부수 작업을 수행할 수 없습니다.     
이러한 필드는 다음과 같이 모두 private으로 변경하고 public 접근자(getter)를 추가해주어야 합니다.     

### Member
````
public class Member {

    private String name;

    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
````

물론 public 클래스의 필드가 불변이라면 직접 노출할 때의 단점은 줄어들지만 여전히 API를 수정하지 않고는 표현방식을   
변경할 수 없고 필드를 읽을 때 부수 작업을 진행할 수 없다는 단점이 존재합니다.    

### MemberFinalField
````
public class MemberFinalField {

    // 불변은 보장할 수 있지만 여전히 단점이 존재한다
    public final String name;

    public final int age;

    public MemberFinalField(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
````

## 정리 

public 클래스는 절대 가변 필드를 직접 노출해서는 안됩니다. 불변 필드라면 덜 위험하지만 여전히 단점이 존재합니다.    
따라서 가능하면 필드를 private으로 선언하고 public 접근자를 제공해야 합니다.      

[아이템 16 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item16)      
[아이템 16 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item16)   
