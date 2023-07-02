package effective.chapter4.item24;

// 정적 멤버 클래스
public class Parent2 {

    private String parentName;

    private int parentAge;

    public Parent2(String parentName, int parentAge) {
        this.parentName = parentName;
        this.parentAge = parentAge;
    }

    static class Child {
        private String childName;

        private int childAge;

        public Child(String childName, int childAge) {
            this.childName = childName;
            this.childAge = childAge;
        }
    }
}
