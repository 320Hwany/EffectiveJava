package effective.chapter9.item64;

public class Eagle implements Animal {
    @Override
    public String cry() {
        return "Eagle cry";
    }

    @Override
    public String eat() {
        return "Eagle eat";
    }

    // 구체 클래스만 제공하는 기능
    public String fly() {
        return "Eagle fly";
    }
}
