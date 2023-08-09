package effective.chapter11.item85;

import java.util.Objects;

// 직렬화 말고 Json으로 만들 수 있도록 설계, 기본 생성자, getter를 만들어준다
public class Member {

    private String name;
    private int age;

    public Member() {
    }

    public Member(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
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
