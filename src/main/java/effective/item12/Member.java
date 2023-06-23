package effective.item12;

public class Member {

    private String name;

    private int age;

    private float height;

    public Member(String name, int age, float height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "MemberCloneable{" +
                "이름 = '" + name + '\'' +
                ", 나이 = " + age + "세" +
                ", 키 = " + height + "cm" +
                '}';
    }
}
