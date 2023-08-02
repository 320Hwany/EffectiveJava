# 아이템 79 - 과도한 동기화는 피하라

과도한 동기화는 성능을 떨어뜨리고 교착 상태에 빠뜨리고 심지어 예측할 수 없는 동작을 낳기도 합니다.    
응답 불가와 안전 실패를 피하려면 동기화 메소드나 동기화 블록 안에서는 제어를 절대로 클라이언트에 양도하면 안됩니다.     

###  동기화 블록 안에서 외계인 메소드를 호출한다 - 잘못된 코드

````java
public class ObservableSet<E> extends ForwardingSet<E> {

    public ObservableSet(Set<E> set) {
        super(set);
    }

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized(observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized(observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element);
        return result;
    }
}
````

### 동기화 블록 안에서 외계인 메소드를 호출한다 - 예외 발생

````java
@Test
void test1() {
    // given
    ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

    set.addObserver(new SetObserver<>() {
        @Override
        public void added(ObservableSet<Integer> s, Integer e) {
            System.out.println(e);
            if (e == 23) {
                s.removeObserver(this);
            }
        }
    });

    // expected
    assertThatThrownBy(() -> set.add(23))
            .isInstanceOf(ConcurrentModificationException.class);
}
````

이를 해결하기 위해서 외계인 메소드를 동기화 블록 바깥으로 옮길 수 있습니다.       

````java
private void notifyElementAdded(E element) {
    List<SetObserverBetter1<E>> snapshot = null;
    synchronized (observers) {
        snapshot = new ArrayList<>(observers);
    }
    for (SetObserverBetter1<E> observer : snapshot) {
        observer.added(this, element);
    }
}
````

외계인 메소드 호출을 동기화 블록 바깥으로 옮기는 더 나은 방법이 있는데 자바의 동시성 컬렉션 라이브러리의    
CopyOnWriteArrayList를 활용하는 것입니다. 이것은 ArrayList를 구현한 클래스로 내부를 변경하는 작업은    
항상 깨끗한 복사본을 만들어 수행하도록 구현했습니다.     
수정할 일이 드물고 순회만 빈번히 일어나는 관찰자 리스트 용도로 최적입니다.    

````java
private final List<SetObserverBetter2<E>> observers = new CopyOnWriteArrayList<>();

public void addObserver(SetObserverBetter2<E> observer) {
    observers.add(observer);
}

public boolean removeObserver(SetObserverBetter2<E> observer) {
    return observers.remove(observer);
}

private void notifyElementAdded(E element) {
    for (SetObserverBetter2<E> observer : observers) {
        observer.added(this, element);
    }
}
````

기본 규칙은 동기화 영역에서는 가능한 한 일을 적게하는 것입니다.    
멀티코어가 일반화된 오늘날, 과도한 동기화가 초래하는 진짜 비용은 락을 얻는데 걸리는 CPU 시간이 아니라     
바로 경쟁하느라 낭비하는 시간, 즉 병렬로 실행할 기회를 잃고 모든 코어가 메모리를 일관되게 보기 위한   
지연 시간이 진짜 비용입니다.     

가변 클래스를 작성하려거든 첫 번째 동기화를 전혀 하지 말고 그 클래스를 동시에 사용해야 하는 클래스가    
외부에서 알아서 동기화하게 만들어야 합니다.   
두 번째 동기화를 내부에서 수행해 스레드 안전한 클래스로 만들어야 합니다.     
java.util은 대부분 첫 번째 방식을 취했고 java.util.concurrent는 두 번째 방식을 취했습니다.     

## 정리

교착상태와 데이터 훼손을 피하려면 동기화 영역 안에서 외계인 메소드를 절대 호출하지 말아야 합니다.    
일반화해 이야기하면 동기화 영역 안에서의 작업은 최소한으로 줄여야합니다.    
가변 클래스를 설계할 때는 스스로 동기화해야 할지 고민해야 합니다.    
멀티코어 세상인 지금은 과도한 동기화를 피하는게 어느 때보다 증요합니다.          
합당한 이유가 있을 때만 내부에서 동기화하고 동기화했는지 여부를 문서에 명확히 밝혀야 합니다.

[아이템 79 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter11/item79)                                                                                               
[아이템 79 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter11/item79)        
