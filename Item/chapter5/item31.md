# 아이템 31 - 한정적 와일드카드를 사용해 API 유연성을 높이라

매개변수화 타입은 불공변입니다. 즉 서로 다른 타입 Type1, Type2가 있을 때  
`List<Type1>`는 `List<Type2>`의 하위 타입도 상위 타입도 아닙니다.    
따라서 `List<String>`은 `List<Object>`의 하위 타입이 아닙니다.   
`List<String>`은 `List<Object>`가 하는 일을 제대로 수행하지 못하니 하위타입이 될 수 없습니다.     
(리스코프 치환 원칙)      
이러한 방식보다 유연한 무언가가 필요하기 때문에 한정적 와일드카드 타입을 제공합니다.   

## 생산자, 소비자 와일드카드 타입

펙스(PESC) : producer-extends, consumer-super         
매개변수화 타입 T가 생산자라면 `<? extends T>`를 사용하고       
소비자라면 `<? super T>`를 사용해야 합니다.  

### Stack
````
public class Stack<E> {

    ...

    public boolean isEmpty() {
        return size == 0;
    }

    // 생산자 매개변수에 와일드카드 타입 적용
    public void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    // 소비자 매개변수에 와일드카드 타입 적용
    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    ...
}
````
위와 같이 pushAll 메소드에서 src 매개변수는 Stack이 사용할 E를 생산하므로   
src의 적절한 타입은 `Iterable<? extends E>`입니다.   
popAll 메소드에서 dst 매개변수는 E 인스턴스를 소비하므로 dst의 적절한 타입은   
`Collection<? super E>`입니다.      

## 타입 매개변수와 와일드카드

타입 매개변수와 와일드카드에는 공통되는 부분이 있어서 메소드를 정의할 때 둘 중 어느 것을  
사용해도 괜찮은 경우가 많습니다. 두 경우를 한번 비교해 보겠습니다.     

### Swap
````
public class Swap {

    public static <E> void swap1(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swap2(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
````

swap1보다는 swap2가 더 간단합니다. 따라서 메소드 선언에 타입 매개변수가 한 번만 나온다면   
와일드카드를 사용하는 것이 더 좋은 방법일 것입니다.        
하지만 그냥 사용하면 컴파일이 되지 않은 오류가 있기 때문에 위와 같이 private 도우미 메소드를 사용합니다.   
더 복잡하다고 생각할 수 있지만 swap2는 public API이기 때문에 그럴만한 가치가 있습니다.      

## 정리

조금 복잡하더라도 와일드카드 타입을 적용하면 API가 훨씬 유연해집니다.    
널리 쓰일 라이브러리를 작성한다면 반드시 와일드카드 타입을 적절히 사용해줘야 합니다.   
PECS 공식은 생산자(producer)는 extends를 소비자(consumer)는 super를 사용한다는 것입니다.    
Comparable과 Comparator는 모두 소비자라는 사실도 기억하면 좋을 것 같습니다.         

[아이템 31 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item31)                                  
[아이템 31 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item31)          

