package effective.chapter12.item90;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class SerializationProxy implements Serializable {

    private final Date start;
    private final Date end;

    public SerializationProxy(final Period p) {
        this.start = p.start();
        this.end = p.end();
    }

    private static final long serialVersionUID = 239284274294732L; // 랜덤 값

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("프록시가 필요합니다");
    }
}
