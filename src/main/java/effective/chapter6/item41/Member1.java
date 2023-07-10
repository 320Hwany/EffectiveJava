package effective.chapter6.item41;

import java.io.Serializable;

public class Member1 implements Serializable {

    private String name;

    private int age;

    public Member1(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
