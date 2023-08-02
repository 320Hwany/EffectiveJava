package effective.chapter11.chapter78;

import java.util.concurrent.atomic.AtomicLong;

// java.util.concurrent.atomic을 이용한 락-프리 동기화
public class Hello2 {

    private static final AtomicLong nextSerialNum = new AtomicLong();

    public static long generateSerialNumber() {
        return nextSerialNum.getAndIncrement();
    }
}
