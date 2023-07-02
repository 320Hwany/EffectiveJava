package effective.chapter4.item24;

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
