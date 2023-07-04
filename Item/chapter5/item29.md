# 아이템 29 - 이왕이면 제네릭 타입으로 만들라

JDK가 제공하는 제네릭 타입과 메소드를 사용하는 일은 일반적으로 쉬운 편이지만      
제네릭 타입을 새로 만드는 일은 조금 어렵습니다.   
다음과 같이 제네릭을 사용하지 않은 Object 기반 스택을 리팩토링 해보겠습니다.        

## Stack
````
public class Stack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
````

이 클래스는 원래 제네릭 타입이어야 마땅합니다.    
지금 상태에서의 클라이언트는 스택에서 꺼낸 객체를 형변환해야 하는데 이때 런타임 오류가 발생할 위험이  
있기 때문입니다. 하지만 배열과 제네릭은 서로 잘 어우러지지 않습니다.      
여기에 적절한 해결책 2가지를 소개하겠습니다.   
첫 번째는 제네릭 배열 생성을 금지하는 제약을 대놓고 우회하는 방법입니다.    

### Stack1
````
// 배열을 사용한 코드를 제네릭으로 만드는 방법 - 1
// 배열의 런타임 타입이 컴파일 타임 타입과 달라 힙 오염이 발생할 수 있습니다
public class Stack1<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // 배열 elements는 push(E)로 넘어온 E 인스턴스만 담는다.
    // 따라서 타입 안전성을 보장하지만 이 배열의 런타임 타입은 E[]가 아닌 Object[]다
    @SuppressWarnings("unchecked")
    public Stack1() {
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
````

이 방법은 가독성이 좋고 형변환을 배열 생성시 단 한번만 해주면 되지만 배열의 런타임 타입이    
컴파일 타임 타입과 달라 힙 오염이 발생할 수 있습니다.    

### Stack2
````
// 배열을 사용한 코드를 제네릭으로 만드는 방법 - 2
public class Stack2<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack2() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        // push에서 E 타입만 혀용하므로 이 형변환은 안전하다.
        @SuppressWarnings("unchecked") E result = (E) elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
````

두 번째 방식은 배열에서 원소를 읽을 때마다 형변환을 해줘야 합니다.   
하지만 pop() 메소드의 반환타입이 E 인 것을 컴파일 타임에 결정할 수 있습니다.     

물론 '아이템 28 - 배열보다는 리스트를 우선하라'와 모순되어 보이지만 우선적으로 고려하라는 것이지   
항상 제네릭 타입으로의 변환이 가능한 것은 아닙니다.   
대다수의 제네릭 타입은 타입 매개변수에 아무런 제약을 두지 않습니다.   
````
Stack<Object>, Stack<int[]>, Stack<List<String>>, Stack
````
위와 같이 어떤 참조 타입으로도 Stack을 만들 수 있습니다. 단 박싱되지 않은 기본 타입은 사용할 수 없습니다.        

## 정리

클라이언트에서 직접 형변환해야 하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편합니다.   
그러니 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록 해야합니다.   
그렇게 하려면 제네릭 타입으로 만들어야 하는 경우가 많습니다.  

[아이템 29 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item29)                         
[아이템 29 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item29)     