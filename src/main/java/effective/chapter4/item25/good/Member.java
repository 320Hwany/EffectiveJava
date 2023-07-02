package effective.chapter4.item25.good;

// 두 클래스를 정적 멤버 클래스로 나눈 방법 - good
public class Member {

    public static class Member1 {
        String NAME = "member1 이름";

        public String getNAME() {
            return NAME;
        }
    }

    public static class Member2 {
        String NAME = "member2 이름";

        public String getNAME() {
            return NAME;
        }
    }

}
