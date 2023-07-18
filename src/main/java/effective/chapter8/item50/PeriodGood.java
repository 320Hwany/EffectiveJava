package effective.chapter8.item50;

import java.util.Date;

public class PeriodGood {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
    public PeriodGood(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    // 수정자 접근자 - 필드의 방어적 복사본을 반환한다
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
