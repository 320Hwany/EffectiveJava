# 아이템 25 - 톱레벨 클래스는 한 파일에 하나만 담으라

소스 파일 하나에 톱레벨 클래스를 여러 개 선언하더라도 자바 컴파일러는 불평하지 않습니다.   
하지만 이렇게 하면 한 클래스를 여러 가지로 정의할 수 있으며, 그 중 어느 것을 사용할지는  
어느 소스 파일을 먼저 컴파일하느냐에 따라 달라집니다.    
컴파일러에 어느 소스 파일을 먼저 건네느냐에 따라 동작이 달라지므로 반드시 바로 잡아야 할 문제입니다.    

해결방법은 간단하게도 톱레벨 클래스들을 서로 다른 파일로 분리하면 됩니다.   
하지만 두 클래스가 관련이 있다고 생각하면 정적 멤버 클래스의 사용을 고려해 볼 수 있습니다.   

### 두 클래스를 한 파일에 정의 - Bad
````java
class Member1 {
    static final String NAME = "member1 이름";
}

class Member2 {
    static final String NAME = "member2 이름";
}
````

### 두 클래스를 정적 멤버 클래스로 나눈 방법 - Good
````java
public class Member {

    public static class Member1 {
        String NAME = "member1 이름";

        public String getNAME() {
            return NAME;
        }
    }

    public static class Member2 {
        String NAME = "member2 이름";

        public String getNAME() {
            return NAME;
        }
    }
}
````

## 정리

소스 파일 하나에는 반드시 톱레벨 클래스를 하나만 담아야 합니다. 이 규칙을 따른다면 컴파일러가 한 클래스에 대한  
정의를 여러 개 만들어 내는 일은 사라집니다. 소스 파일을 어떤 순서로 컴파일하든 바이너리 파일이나 프로그램의 동작이   
달라지는 일은 결코 일어나지 않습니다.   
꼭 하나의 클래스로 묶어야 한다면 정적 멤버 클래스를 사용하는 방법을 고려해볼 수 있습니다.      

[아이템 25 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item25)                  
[아이템 25 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item25)        