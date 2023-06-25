package effective.chapter4.item15;

public class Member {

    public String name;

    protected int age;

    float height;

    private float weight;

    public Member(String name, int age, float height, float weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    protected void hello() {
        System.out.println("hello");
    }
}
