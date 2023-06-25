package effective.chapter3.item10;

import java.util.Objects;

public class Member {

    private final String name;
    private final int age;
    private final float height;

    public Member(String name, int age, float height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age && Float.compare(member.height, height) == 0 && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, height);
    }
}
