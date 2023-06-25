package effective.chapter3.item14;

import java.util.Comparator;

public class MemberWithComparator implements Comparable<MemberWithComparator> {

    private String name;

    private int age;

    private float height;

    private float weight;

    public MemberWithComparator(String name, int age, float height, float weight) {
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

    private static final Comparator<MemberWithComparator> COMPARATOR =
            Comparator.comparingInt((MemberWithComparator m) -> m.age)
                    .thenComparingDouble(m -> m.height)  // float는 double용을 이용해 수행할 수 있다
                    .thenComparingDouble(m -> m.weight);

    @Override
    public int compareTo(MemberWithComparator m) {
        return COMPARATOR.compare(this, m);
    }
}
