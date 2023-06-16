package effective.item2;

public class Member {

    private String name;
    private int age;
    private String email;
    private double height;
    private double weight;

    public Member() {
    }

    public Member(String name, int age, String email, double height, double weight) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
