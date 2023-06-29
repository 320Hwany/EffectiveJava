package effective.chapter4.item19;

public class Tiger extends Animal {

    private final String name;

    public Tiger() {
        name = "tiger";
    }

    @Override
    public void hello() {
        System.out.println(name);
    }

    public String getName() {
        return name;
    }
}
