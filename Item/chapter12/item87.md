# 아이템 87 - 커스텀 직렬화 형태를 고려해보라

어떤 객체의 기본 직렬화 형태는 그 객체를 루트로 하는 객체 그래프의 물리적 모습을 나름 효율적으로 인코딩합니다.   
다시 말해, 객체가 포함한 데이터들과 그 객체에서부터 시작해 접근할 수 있는 모든 객체를 담아내며    
심지어 이 객체들이 연결된 위상까지 기술합니다.    
하지만 이상적인 직렬화 형태라면 물리적인 모습과 독립된 논리적인 모습만을 표현해야 합니다.    

객체의 물리적 표현과 논리적 내용이 같다면 기본 직렬화 형태라도 무방합니다.    

### 기본 직렬화 형태에 적합한 후보

````java
public class Name implements Serializable {

    private final String lastName;

    private final String firstName;

    private final String middelName;

    public Name(final String lastName, final String firstName, final String middelName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middelName = middelName;
    }
}
````

기본 직렬화 형태가 적합하다고 결정했더라도 불변식 보장과 보안을 위해 readObject 메소드를 제공해야 할 때가 많습니다.     

### 기본 직렬화 형태에 적합하지 않은 클래스

````java
public final class StringListBad implements Serializable {

    private int size = 0;

    private Entry head = null;

    private static class Entry implements Serializable {
        String data;
        Entry next;
        Entry previous;
    }
}
````

객체의 물리적 표현과 논리적 표현의 차이가 클 때 기본 직렬화 형태를 사용하면 크게 4가지 문제가 생깁니다.       

1. 공개 API가 현재의 내부 표현 방식에 영구히 묶인다
2. 너무 많은 공간을 차지할 수 있다
3. 시간이 너무 많이 걸린다
4. 스택 오버플로를 일으킬 수 있다

### 합리적인 커스텀 직렬화 형태를 갖춘 StringList

````java
public final class StringListBetter implements Serializable {

    // 무작위로 고른 long 값을 할당
    private static long serialVersionUID;

    private transient int size = 0;

    private transient Entry head = null;

    private static class Entry {
        String data;
        Entry next;
        Entry previous;
    }

    public final void add(String s) {

    }

    private synchronized void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(size);

        for (Entry e = head; e != null; e = e.next) {
            s.writeObject(e.data);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int numElements = s.readInt();

        for (int i = 0; i < numElements; i++) {
            add((String) s.readObject());
        }
    }
}
````

StringListBetter의 필드 모두가 transient더라도 writeObject와 readObject는 각각 가장 먼저     
defaultWriteObject와 defaultReadObject를 호출합니다.     
이렇게 해야 향후 릴리스에서 transient가 아닌 인스턴스 필드가 추가되더라도 상호 호환되기 때문입니다.    

객체를 직렬화한 후 역직렬화하면 원래 객체를 그 불변식까지 포함해 제대로 복원해낸다는 점에서 정확하다고 할 수 있습니다.      
하지만 그 불변식이 해시 테이블의 사용과 같이 세부 구현에 따라 정확성이 깨질 수도 있습니다.     

또한 기본 직렬화 사용 여부와 상관없이 객체의 전체 상태를 읽는 메소드에 적용해야 하는 동기화 메커니즘을 직렬화에도    
적용해야 합니다.     

## 정리

클래스를 직렬화하기로 했다면 어떤 직렬화 형태를 사용할 지 심사숙고해서 결정해야 합니다.    
자바의 기본 직렬화 형태는 객체를 직렬화한 결과가 해당 객체의 논리적 표현에 부하할 때만 사용하고   
그렇지 않으면 객체를 적절히 설명하는 커스텀 직렬화 형태를 고안해야 합니다.    
직렬화 형태도 공개 메소드를 설계할 때에 준하는 시간을 들여 설계해야 합니다.    
한번 공개된 메소드는 향후 릴리스에서 제거할 수 없듯이 직렬화 형태에 포함된 필드도 마음대로 제거할 수 없습니다.   
직렬화 호환성을 유지하기 위해 영원히 지원해야 하는 것입니다.    
잘못된 직렬화 형태를 선택하면 해당 클래스의 복잡성과 성능에 영구히 부정적인 영향을 남깁니다.       

[아이템 87 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item87)     