package effective.chapter8.item53;

public class VariableArgument {

    // 간단한 가변인수 활용 예
    public static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    // 인수가 1개 이상이어야 하는 가변인수 메소드 - 잘못 구현한 예!
    public static int minBad(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("인수가 1개 이상 필요합니다");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) {
                min = args[i];
            }
        }
        return min;
    }

    // 인수가 1개 이상이어야 할 때 가변인수를 제대로 사용하는 방법
    public static int minGood(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }
}
