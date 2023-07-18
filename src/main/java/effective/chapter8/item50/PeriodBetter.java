package effective.chapter8.item50;

import java.util.Date;

// 하지만 여전히 접근자 메소드가 내부의 가변 정보를 직접 드러냄
public class PeriodBetter {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
    public PeriodBetter(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
