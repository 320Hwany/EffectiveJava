# 아이템 24 - 멤버 클래스는 되도록 static으로 만들라

중첩 클래스란 다른 클래스 안에 정의된 클래스를 말합니다. 중첩 클래스는 자신을 감싼 바깥 클래스에서만   
쓰여야 하며, 그 외의 쓰임새가 있다면 톱레벨 클래스로 만들어야 합니다.   
중첩 클래스의 종류는 정적 멤버 클래스, 비정적 멤버 클래스, 익명 클래스, 지역 클래스 이렇게 4가지입니다.   

## 정적 멤버 클래스

## Calculator
````
// 정적 멤버 클래스
public class Calculator {

    static class Operation {
        public int plus(int a, int b) {
            return a + b;
        }
    }
}
````

Calculator 안에 Operation을 비롯한 여러 정적 멤버 클래스를 정의할 수 있습니다.   
이때 Calculator를 인스턴스화 하지 않고도 사용할 수 있습니다.    

````
@Test
@DisplayName("Calculator 안에 Operation 정적 멤버 클래스를 정의하여 사용합니다")
void test3() {
    // given
    Calculator.Operation operation = new Calculator.Operation();

    // when
    int sum = operation.plus(10, 2);
    
    // then
    assertThat(sum).isEqualTo(30);
}
````

## 비정적 멤버 클래스

비정적 멤버 클래스의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결됩니다.  
이 둘 사이의 관계는 멤버 클래스가 인스턴스화될 때 확립되며 비정적 멤버 클래스는 바깥 클래스의 인스턴스   
없이는 생성할 수 없습니다.    

### Parent1
````
// 비정적 멤버 클래스
public class Parent1 {

    private String parentName;

    private int parentAge;

    public Parent1(String parentName, int parentAge) {
        this.parentName = parentName;
        this.parentAge = parentAge;
    }

    class Child {
        private String childName;

        private int childAge;

        public Child(String childName, int childAge) {
            this.childName = childName;
            this.childAge = childAge;
        }

        public String getParentName() {
            return Parent1.this.parentName;
        }
    }
}
````

여기서 Child는 Parent1 인스턴스 없이는 생성할 수 없고 둘의 관계가 밀접하게 연결되어 있습니다.   
따라서 멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여야 합니다.   
static을 생략하면 바깥 인스턴스로의 숨은 외부 참조를 갖게 되기 때문입니다.

## 익명 클래스

익명 클래스는 쓰이는 시점에 선언과 동시에 인스턴스가 만들어집니다.  
코드의 어디서든 만들 수 있으며 오직 비정적인 문맥에서 사용될 때만 바깥 클래스의 인스턴스를   
참조할 수 있습니다.   

### Member1
````
// 학습 테스트에서 익명 클래스로 사용
public class Member1 {

    String hello = "hello";

    public String myNameIs() {
        return null;
    }
}
````

````
@Test
@DisplayName("익명 클래스를 사용하여 Member의 메소드를 재정의할 수 있습니다")
void test4() {
    // given
    Member1 member = new Member1() {

        private final String name = "이름";
        public String myNameIs() {
            return name;
        }
    };

    // when
    String returnName = member.myNameIs();
    String hello = member.hello;

    // then
    assertThat(returnName).isEqualTo("이름");
    assertThat(hello).isEqualTo("hello"); // 비정적인 문맥에서 사용될 때 바깥 클래스의 인스턴스 참조 가능
}
````

## 지역 클래스

### Member2
````
// 지역 클래스
public class Member2 {

    private String name;

    private int age;

    public String getChildName() {
        class Child {
            private final String childName = "자식 이름";
            private final int childAge = 20;
        }

        Child child = new Child();
        return child.childName;
    }
}
````

````
@Test
@DisplayName("메소드 안에 지역 클래스를 정의하여 사용할 수 있습니다")
void test5() {
    // given
    Member2 member2 = new Member2();

    // when
    String childName = member2.getChildName();

    // then
    assertThat(childName).isEqualTo("자식 이름");
}
````

## 정리

중첩 클래스에는 4가지가 있으며 각각 쓰임새가 다릅니다. 메소드 밖에서도 사용해야 하거나   
메소드 안에 정의하기엔 너무 길다면 멤버 클래스로 만들 수 있습니다.   
이때 멤버 클래스의 인스턴스 각각이 바깥 인스턴스를 참조한다면 비정적으로 그렇지 않으면  
정적으로 만들어야 합니다.    
중첩 클래스가 한 메소드 안에서만 쓰이면서 그 인스턴스를 생성하는 지점이 단 한곳이고  
해당 타입으로 쓰기에 적합한 클래스나 인터페이스가 이미 있다면 익명 클래스로 만들 수 있습니다.   
그렇지 않다면 지역 클래스를 고려해볼 수 있습니다.    

[아이템 24 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter4/item24)               
[아이템 24 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter4/item24) 