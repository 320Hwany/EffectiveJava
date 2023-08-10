package effective.chapter12.item86;

import java.io.Serializable;
import java.util.Objects;

public class Member implements Serializable {

    private String name;

    private int age;

    public Member(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
