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

    public void win1() throws Exception {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new Exception();
        }
    }

    public void win2() {
        if (lotto == true) {
            System.out.println("win");
        } else {
            throw new RuntimeException();
        }
    }

    public boolean check() {
        return lotto;
    }
}
