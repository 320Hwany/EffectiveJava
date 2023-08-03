package effective.chapter11.item80;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

public class ExecutorTest {

    @Test
    @DisplayName("실행할 태스크를 넘기며 만약 완료되지 않으면 최대 5초까지 기다립니다")
    void test1() throws InterruptedException {
        // given
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> System.out.println("hello world");

        // when
        exec.execute(runnable);
        exec.shutdown();
        exec.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("포크-조인 태스크를 이용해 구성하는 스레드들이 이 태스크를 처리하도록 합니다")
    void test2() {
        // given
        int number1 = 5;
        int number2 = 10;

        // when
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        AddNumbersTask task = new AddNumbersTask(number1, number2);
        int result = forkJoinPool.invoke(task);

        // then
        assertThat(number1 + number2).isEqualTo(result);
    }

    static class AddNumbersTask extends RecursiveTask<Integer> {

        private final int number1;
        private final int number2;

        public AddNumbersTask(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }

        @Override
        protected Integer compute() {
            return number1 + number2;
        }
    }
}
