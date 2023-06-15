package effective.item1;

public class MemberSingleton {

    private String name;
    private int age;

    private static final MemberSingleton memberSingleton = new MemberSingleton();

    private MemberSingleton() {
    }

    public static MemberSingleton getInstance(){
        return memberSingleton;
    }
}
