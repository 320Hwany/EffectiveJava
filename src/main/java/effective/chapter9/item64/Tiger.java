package effective.chapter9.item64;

public class Tiger implements Animal {

    @Override
    public String cry() {
        return "Tiger cry";
    }

    @Override
    public String eat() {
        return "Tiger eat";
    }

    // 구체 클래스만 제공하는 기능
    public String run() {
        return "Tiger run";
    }
}
