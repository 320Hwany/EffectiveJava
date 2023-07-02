package effective.chapter4.item24;

// 비정적 멤버 클래스
public class Parent1 {

    private String parentName;

    private int parentAge;

    public Parent1(String parentName, int parentAge) {
        this.parentName = parentName;
        this.parentAge = parentAge;
    }

    class Child {
        private String childName;

        private int childAge;

        public Child(String childName, int childAge) {
            this.childName = childName;
            this.childAge = childAge;
        }

        public String getParentName() {
            return Parent1.this.parentName;
        }
    }
}
