package effective.chapter8.item51;

// 매개변수 여러 개를 묶어주는 도우미 클래스
public class UpdateDto {

    private String name;
    private int age;
    private float height;
    private float weight;

    public UpdateDto(String name, int age, float height, float weight) {
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
}
