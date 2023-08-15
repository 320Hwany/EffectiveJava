package effective.chapter12.item90;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
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

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;

        public SerializationProxy(final Period p) {
            this.start = p.start();
            this.end = p.end();
        }

        private static final long serialVersionUID = 239284274294732L; // 랜덤 값

        // 직렬화 프록시 패턴용 readObject 메소드
        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("프록시가 필요합니다");
        }

        // Period.SerializationProxy 용 readResolve 메소드
        private Object readResolve() {
            return new Period(start, end); // public 생성자를 사용한다
        }
    }
}
