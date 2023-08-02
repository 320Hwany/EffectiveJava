package effective.chapter11.item78;

import java.util.concurrent.TimeUnit;

// 잘못된 코드 - 이 프로그램은 얼마나 오래 실행될까?
public class StopThreadBad {

    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while(!stopRequested) i++;
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
