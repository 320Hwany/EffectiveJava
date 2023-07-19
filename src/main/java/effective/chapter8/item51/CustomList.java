package effective.chapter8.item51;

import java.util.List;

public class CustomList {

    private List<String> strings;

    public CustomList(List<String> strings) {
        this.strings = strings;
    }

    // 매개변수가 3개인 메소드 - 가능하면 줄이자
    public int findIndexBad(int from, int to, String value) {
        List<String> subList = strings.subList(from, to);
        if (subList.contains(value)) {
            return strings.indexOf(value);
        }
        return -1;
    }

    // 2개의 메소드로 분리했다
    public List<String> getSubList(int from, int to) {
        return strings.subList(from, to);
    }

    public int findIndexBetter(List<String> subList, String value) {
        return subList.indexOf(value);
    }
}
