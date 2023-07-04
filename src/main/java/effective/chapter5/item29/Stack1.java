package effective.chapter5.item29;

import java.util.Arrays;
import java.util.EmptyStackException;

// 배열을 사용한 코드를 제네릭으로 만드는 방법 - 1
// 배열의 런타임 타입이 컴파일 타임 타입과 달라 힙 오염이 발생할 수 있습니다
public class Stack1<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

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
