package effective.chapter3.item14;

public class Member implements Comparable<Member> {

    private String name;

    private int age;

    private float height;

    private float weight;

    public Member(String name, int age, float height, float weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
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

    public float getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Member member) {
        int result = Integer.compare(age, member.getAge());
        if (result == 0) {
            result = Float.compare(height, member.getHeight());
            if (result == 0) {
                result = Float.compare(weight, member.getWeight());
            }
        }
        return result;
    }
}
