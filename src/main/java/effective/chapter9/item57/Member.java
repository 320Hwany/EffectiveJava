package effective.chapter9.item57;

import java.util.List;

public class Member {

    private String name;
    private int age;

    private List<String> hobbies;

    public Member(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}
