package effective.chapter9.item63;

import java.util.List;

public class Statement {

    private List<String> items = List.of(
            "dfjdasfdsljfsalfjaslfjsal", "fdsfsfdsafdasfdsfdsa", "itemyuiutyiyuiyiyuiu1", "trttwert",
            "erwqqwewrerw", "rwereqwrewqr", "qewrqwrwer", "itrewreqwrqem2", "vzxvzcxvzxc", "uiuyigasdfsdfa",
            "fadsfsaddfa", "cvvzxvxcv", "itefdsfsdfam1", "cxvczxvxzvxzvzx");

    public int numItems() {
        return items.size();
    }

    // 문자열 연결을 잘못 사용한 예 - 느리다!
    public String statement1() {
        String result = "";
        for (int i = 0; i < numItems(); i++) {
            result += items.get(i);
        }
        return result;
    }

    // StringBuilder를 사용하면 문자열 연결 성능이 크게 개선된다
    public String statement2() {
        StringBuilder b = new StringBuilder(numItems() * 5);
        for (int i = 0; i < numItems(); i++) {
            b.append(items.get(i));
        }
        return b.toString();
    }
}
