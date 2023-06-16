package effective.item2;

public class MemberWithBuilder {

    private String name;
    private int age;
    private String email;
    private double height;
    private double weight;

    public static class Builder {
        private String name = "";
        private int age = 0;
        private String email = "";
        private double height = 0;
        private double weight = 0;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder height(double height) {
            this.height = height;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public MemberWithBuilder build() {
            return new MemberWithBuilder(this);
        }
    }

    private MemberWithBuilder(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.height = builder.height;
        this.weight = builder.weight;
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
}
