package effective.item1;

public class Member {

    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Member toMember(String name, int age) {
        return new Member(name, age);
    }
}
