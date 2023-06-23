package effective.item13;

public class Member implements Cloneable {

    private String name;

    private int age;

    private float height;

    public Member(String name, int age, float height) {
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

    public float getHeight() {
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
