package effective.chapter6.item34;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// 상수별 메소드 구현을 활용한 열거 타입
public enum Operation {

    PLUS("+") {
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract double apply(double x, double y);

    private static final Map<String, Operation> stringToEnum =
            Arrays.stream(values()).collect(Collectors.toMap(op -> op.symbol, op -> op));

    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }

    // 기본 열거 타입에 상수별 동작을 혼합해 넣을 때는 switch 문이 좋은 선택이 될 수 있다
    public static Operation inverse(Operation operation) {
        switch (operation) {
            case PLUS: return MINUS;
            case MINUS: return PLUS;
            case TIMES: return DIVIDE;
            case DIVIDE: return TIMES;

            default:
                throw new AssertionError("알 수 없는 연산: " + operation);
        }
    }
}
