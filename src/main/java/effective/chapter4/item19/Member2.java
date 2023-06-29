package effective.chapter4.item19;

// 상속을 금지하는 두번째 방법 - 생성자 private, public 정적 팩토리
public class Member2 {

    private String name;
    private int age;

    private Member2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static Member2 valueOf(String name, int age) {
        return new Member2(name, age);
    }
}
