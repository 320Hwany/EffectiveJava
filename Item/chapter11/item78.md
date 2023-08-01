# 아이템 78 - 공유 중인 가변 데이터는 동기화해 사용하라

많은 프로그래머가 동기화를 배타적 실행, 즉 한 스레드가 변경하는 중이라서 상태가 일관되지 않은 순간의 객체를   
다른 스레드가 보지 못하게 막는 용도로만 생각합니다.     
먼저 이 관점에서 얘기해보면 한 객체가 일관된 상태를 가지고 생성되고 이 객체 접근하는 메소드는 그 객체에 락을 겁니다.    
락을 건 메소드는 객체의 상태를 확인하고 필요하면 수정합니다.    
즉, 객체를 하나의 일관된 상태에서 다른 일관된 상태로 변화시킵니다.    

맞는 설명이지만 동기화에는 중요한 기능이 하나 더 있습니다.    
동기화 없이는 한 스레드가 만든 변화를 다른 스레드에서 확인하지 못할 수 있다는 것입니다.    
자바 언어 명세는 스레드가 필드를 읽을 때 항상 '수정이 완전히 반영된' 값을 얻는다고 보장하지만     
한 스레드가 저장한 값이 다른 스레드에게 '보이는가'는 보장하지 않습니다.    
따라서 동기화는 배타적 실행뿐 아니라 스레드 사이의 안정적인 통신에 꼭 필요합니다.    

### 잘못된 코드 - 이 프로그램은 얼마나 오래 실행될까?

````java
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
````

이 프로그램은 종료되지 않고 계속 실행됩니다. 동기화하지 않아서 메인 스레드가 수정한 값을   
백그라운드 스레드가 언제 보게 될지 보증할 수 없기 때문입니다.          

### 적절히 동기화해 스레드가 정상 종료한다

````java
public class StopThreadBetter1 {

    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while(!stopRequested()) i++;
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
````

쓰기 메소드와 읽기 메소드 모두를 동기화하여 동작을 보장했습니다.    
이 경우 동기화의 목적인 배타적 수행과 스레드 간 통신 중에서 스레드 간 통신을 수행하기 때문에     
volatile 한정자를 사용할 수도 있습니다. 

### volatile 필드를 사용해 스레드가 정상 종료한다

````java
public class StopThreadBetter2 {

    private static volatile boolean stopRequested;

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
````

volatile 한정자는 배타적 수행과는 상관없지만 항상 가장 최근에 기록된 값을 읽게 됨을 보장합니다.     

### 잘못된 코드 - 동기화가 필요하다

````java
public class Hello1 {

    private static volatile int nextSerialNumber = 0;

    public static int generateSerialNumber() {
        return nextSerialNumber++;
    }
}
````

이 경우 증가 연산자 사용시 값을 읽고 새로운 값을 저장하는 사이에 다른 스레드가 값을 읽어갈 수도 있습니다.    
이런 오류를 '안전 실패'라고 합니다.         

### java.util.concurrent.atomic을 이용한 락-프리 동기화

````java
public class Hello2 {

    private static final AtomicLong nextSerialNum = new AtomicLong();

    public static long generateSerialNumber() {
        return nextSerialNum.getAndIncrement();
    }
}
````

이렇게 동기화를 보장할 수 있습니다.    
하지만 가장 좋은 방법은 애초에 가변 데이터를 공유하지 않고 가변 데이터는 단일 스레드에서만 사용하는 것입니다.    

## 정리

여러 스레드가 가변 데이터를 공유한다면 그 데이터를 읽고 쓰는 동작은 반드시 동기화해야 합니다.    
동기화하지 않으면 한 스레드가 수행한 변경을 다른 스레드가 보지 못할 수도 있습니다.    
배타적 실행은 필요없고 스레드끼리의 통신만 필요하다면 volatile 한정자를 사용할 수 있지만 사용이 까다롭습니다.    
가장 좋은 방법은 가변 데이터는 공유하지 않는 것입니다.        

[아이템 78 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter11/item78)                  