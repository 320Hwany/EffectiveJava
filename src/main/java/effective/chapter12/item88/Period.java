package effective.chapter12.item88;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

// 방어적 복사를 사용하는 불변 클래스, 직렬화할 수 있도록 선언한 것만으로 클래스의 불변식을 깨뜨리는 객체를 만들 수 있음
public final class Period implements Serializable {

    private Date start;
    private Date end;

    public Period(Date start, Date end) {
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

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        // 가변적 요소들을 방어적으로 복사한다
        start = new Date(start.getTime());
        end = new Date(end.getTime());

        // 불변식을 만족하는지 검사한다
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }
}
