# 아이템 7 - 다 쓴 객체 참조를 해제하라

C, C++처럼 메모리를 직접 관리해야 하는 언어와 달리 자바는 가비지 컬렉터를 갖춘 언어입니다.       
하지만 이것이 메모리 관리에 더 이상 신경 쓰지 않아도 됨을 의미하지는 않습니다.    

## Stack

```
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
        return elements[--size];
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```

이 코드에 숨어 있는 문제는 '메모리 누수'입니다. 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수하지 않습니다.     
회수하지 않는 이유는 스택이 자기 메모리를 직접 관리하기 때문입니다.   
자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항상 메모리 누수에 주의해야합니다.      
pop() 메소드를 수정해보겠습니다.     

## pop
```
public Object pop() {
    if (size == 0) {
        throw new EmptyStackException();
    }
    Object result = elements[--size];
    elements[size] = null; // 다 쓴 참조 해제
    return result;
}
```
이렇게 null 처리를 해주면서 객체 참조를 해제할 수 있습니다.    

하지만 객체 참조를 null 처리하는 일은 예외적인 경우여야만 합니다.   
다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위 밖으로 밀어내는 것입니다.    

        
[아이템 7 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item7)                

