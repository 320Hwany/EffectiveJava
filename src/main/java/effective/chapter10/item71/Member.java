package effective.chapter10.item71;

public class Member {

    private String name;

    private int age;

    private boolean lotto = false;

    public Member(String name, int age, boolean lotto) {
        this.name = name;
        this.age = age;
        this.lotto = lotto;
    }

    // 검사 예외를 사용
    public void win1() throws Exception {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new Exception();
        }
    }

    // 비검사 예외를 사용
    public void win2() {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new RuntimeException();
        }
    }

    // 상태 검사 메소드
    public boolean check() {
        return lotto;
    }
}
