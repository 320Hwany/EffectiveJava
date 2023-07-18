package effective.chapter7.item49;

import java.util.Objects;

public class Member {

    private String name;

    private int age;

    public Member(String name, int age) {
        Objects.requireNonNull(name, "이름을 입력해주세요");
        this.name = name;
        this.age = age;
    }

    protected void update(String name, int age) {
        assert name != null;
        assert age >= 0;
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
