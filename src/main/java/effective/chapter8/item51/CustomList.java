package effective.chapter8.item51;

import java.util.List;

public class CustomList {

    private List<String> strings;

    private String value;

    public CustomList(List<String> strings, String value) {
        this.strings = strings;
        this.value = value;
    }

    // 매개변수가 3개인 메소드 - 가능하면 줄이자
    public int findIndex1(List<String> start, List<String> mid, List<String> end) {
        if (start.contains(value)) {
            return strings.indexOf(value);
        } else if (mid.contains(value)) {
            return strings.indexOf(value);
        } else if (end.contains(value)) {
            return strings.indexOf(value);
        }
        return -1;
    }

    // List가 정의한 subList를 이용해 매개변수를 줄임 - 여러 메소드로 쪼갠 케이스
    public int findIndex2(int from, int to) {
        List<String> subList = strings.subList(from, to);
        if (subList.contains(value)) {
            return strings.indexOf(value);
        }
        return -1;
    }
}
