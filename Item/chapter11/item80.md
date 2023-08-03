# 아이템 80 - 스레드보다는 실행자, 태스크, 스트림을 애용하라

java.util.concurrent 패키지의 등장으로 실행자 프레임워크라고 하는 인터페이스 기반의    
유연한 태스크 실행 기능이 가능해졌습니다.     

### 실행할 태스크를 넘기며 5초를 기다린 후 실행자를 종료합니다

다음과 같이 실행자와 태스크를 만들고      

````java
ExecutorService exec = Executors.newSingleThreadExecutor();
Runnable runnable = () -> System.out.println("hello world");
````

실행자에 태스크를 넘겨 실행하며 5초를 기다린 후 실행자를 종료시킬 수 있습니다.

````java
exec.execute(runnable);
exec.awaitTermination(5, TimeUnit.SECONDS);
exec.shutdown();
````
 

작업 큐를 손수 만드는 일은 삼가야 하고 스레드를 직접 다루는 것도 일반적으로 삼가야 합니다.    
스레드를 직접 다루면 Thread가 작업 단위와 수행 매커니즘 모두를 수행하게 됩니다.     
반면 실행자 프레임워크에서는 작업 단위와 실행 매커니즘이 분리됩니다.    
작업 단위를 나타내는 핵심 추상 개념이 태스크입니다.     


### 포크-조인 태스크를 이용해 구성하는 스레드들이 이 태스크를 처리하도록 합니다

````java
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
````

````java
@Test
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
````

포크-조인 태스크, 즉 ForkJoinTask의 인스턴스는 작은 하위 태스크로 나뉠 수 있고 ForkJoinPool을 구성하는   
스레드들이 이 태스크들을 처리하며 일을 먼저 끝낸 스레드는 다른 스레드의 남은 태스크를 가져와 대신 처리할 수도 있습니다.    
이렇게 하여 모든 스레드가 바쁘게 움직여 CPU를 최대한 활용하면서 높은 처리량과 낮은 지연시간을 달성합니다.      
이러한 포크-조인 태스크를 직접 작성하고 튜닝하기란 어려운 일이지만 포크-조인 풀을 이용해 만든 병렬 스트림을 이용하면      
적은 노력으로 그 이점을 누릴 수 있습니다.        

[아이템 80 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter11/item80)      