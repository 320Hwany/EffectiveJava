package effective.item13;

public class Member implements Cloneable {

    private String name;

    private int age;

    private int height;

    public Member(String name, int age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Member clone() {
        try {
            Member clone = (Member) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
