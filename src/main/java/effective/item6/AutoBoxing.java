package effective.item6;

public class AutoBoxing {

    private Long sumLong = 0L;
    private long sumlong = 0;

    public long sumBadWay() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sumLong += i;
        }
        return sumLong;
    }

    public long sumGoodWay() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sumlong += i;
        }
        return sumlong;
    }
}
