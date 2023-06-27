package effective.chapter4.item16;

public class MemberFinalField {

    // 불변은 보장할 수 있지만 여전히 단점이 존재한다
    public final String name;

    public final int age;

    public MemberFinalField(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
