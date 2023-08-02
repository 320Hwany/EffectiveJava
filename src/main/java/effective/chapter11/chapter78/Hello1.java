package effective.chapter11.chapter78;

// 잘못된 코드 - 동기화가 필요하다
public class Hello1 {

    private static volatile int nextSerialNumber = 0;

    public static int generateSerialNumber() {
        return nextSerialNumber++;
    }
}
