package effective.chapter4.item22;

public enum HelloConstantSingleton {

    HELLO("hello"),
    WORLD("world");

    private final String value;

    HelloConstantSingleton(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
