package effective.chapter8.item51;

public class Member {

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

    // 매개변수 목록이 긴 update 메소드
    public void update1(String name, int age, float height, float weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    // 매개변수 여러 개를 묶어주는 도우미 클래스를 사용한 update 메소드
    public void update2(UpdateDto dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
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
}
