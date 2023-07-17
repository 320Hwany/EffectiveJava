# 아이템 47 - 반환 타입으로는 스트림보다 컬렉션이 낫다

아이템 45에서 이야기했듯이 스트림은 반복을 지원하지 않습니다.    
따라서 스트림과 반복을 알맞게 조합해야 좋은 코드가 나옵니다.     

### Stream<E>를 Iterable<E>로 중개해주는 어댑터

````
private static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return stream::iterator;
}
````

### Iterable<E>를 Stream<E>로 중개해주는 어댑터

````
private static <E> Stream<E> streamOf(Iterable<E> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
}
````

위와 같이 스트림을 Iterable로 Iterable을 스트림으로 중개하는 어댑터를 사용할 수 있습니다.    
메소드가 오직 스트림 파이프라인에서만 쓰일 걸 안다면 스트림으로 반환을 하고   
반대로 반환된 객체들이 반복문에서만 쓰일 걸 안다면 Iterable을 반환할 수 있습니다.    
하지만 공개 API를 작성할 때는 스트림 파이프라인을 사용하는 사람과 반복문에서 쓰려는 사람 모두를 고려해야 합니다.   

Collection 인터페이스는 Iterable의 하위 타입이고 stream 메소드도 제공하니 반복과 스트림 동시에 지원합니다.    
따라서 원소 시퀀스를 반환하는 공개 API의 반환 타입에는 Collection이나 그 하위 타입을 쓰는게 일반적으로 최선입니다.     

### 입력 집합의 멱집합을 전용 컬렉션에 담아 반환한다

컬렉션을 반환하는 것이 원소의 개수가 부담된다면 전용 컬렉션을 구현하는 방법도 있습니다.    

````
public class PowerSet {

    public static final <E> Collection<Set<E>> of(Set<E> s) {
        List<E> src = new ArrayList<>(s);
        if (src.size() > 30) {
            throw new IllegalArgumentException("집합에 원소가 너무 많습니다(최대 30개).: " + s);
        }

        return new AbstractList<Set<E>>() {
            @Override
            public int size() {
                return 1 << src.size();
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set) o);
            }

            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();
                for (int i = 0; index != 0; index++) {
                    if ((index & 1) == 1) {
                        result.add(src.get(i));
                    }
                }
                return result;
            }
        };
    }
}
````

## 정리

원소 시퀀스를 반환하는 메소드를 작성할 때는 이를 스트림으로 처리하기를 원하는 사용자와 반복으로 처리하기를  
원하는 사용자가 모두 있을 수 있음을 떠올리고 양쪽을 다 만족시키려 노력해야 합니다.   
컬렉션을 반환할 수 있다면 최선의 선택입니다.    
반환 전부터 이미 원소들을 컬렉션에 담아 관리하고 있거나 컬렉션을 하나 더 만들어도 될 정도로 원소 개수가 적다면      
ArrayList 같은 표준 컬렉션에 담아 반환하고 그렇지 않으면 앞서의 멱집합 예처럼 전용 컬렉션을 구현할 수도 있습니다.
컬렉션을 반환하는 것이 불가능하다면 스트림과 Iterable 중 더 자연스러운 것을 반환하면 됩니다.      

[아이템 47 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter7/item47)                                                                           
[아이템 47 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter7/item47)     