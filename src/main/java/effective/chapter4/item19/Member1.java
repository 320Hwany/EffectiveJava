package effective.chapter4.item19;

// 상속을 금지하는 첫번째 방법 - final 클래스
public final class Member1 {

    private String name;
    private int age;

    public Member1(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
