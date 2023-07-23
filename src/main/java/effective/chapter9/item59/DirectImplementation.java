package effective.chapter9.item59;

import java.util.Random;

// 라이브러리를 사용하지 않고 직접 구현 - 문제를 내포
public class DirectImplementation {

    private static Random rnd = new Random();

    public static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }
}
