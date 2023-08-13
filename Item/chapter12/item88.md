# 아이템 88 - readObject 메소드는 방어적으로 작성하라

아이템 50에서는 불변인 날짜 범위 클래스를 만드는데 가변인 Date 필드를 이용했습니다.    
그래서 불변식을 지키고 불변을 유지하기 위해 생성자와 접근자에서 Date 객체를 방어적으로 복사하느라 코드가 길어졌습니다.

### 방어적 복사를 사용하는 불변 클래스

````java
public final class Period {

    private final Date start;
    private final Date end;

    // 수정한 생성자 - 매개변수의 방어적 복사본을 만든다
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    // 수정자 접근자 - 필드의 방어적 복사본을 반환한다
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
````

Period 객체의 물리적 표현이 논리적 표현과 부합하므로 기본 직렬화 형태를 사용해도 나쁘지는 않습니다.    
그래서 이 클래스 선언에 implements Serializable을 추가하는 것으로 모든 일을 끝낼 수 있을 것 같지만    
이렇게 해서는 이 클래스의 주요한 불변식을 더는 보장하지 못합니다.     
문제는 readObject 메소드가 실질적으로 또 다른 public 생성자이기 때문입니다.    
쉽게 말해 readObject는 매개변수로 바이트 스트림을 받는 생성자라 할 수 있습니다.        
Period를 직렬화할 수 있도록 선언한 것만으로 클래스의 불변식을 깨뜨리는 객체를 만들 수 있는 것입니다.    

객체를 역직렬화할 때는 클라이언트가 소유해서는 안되는 객체 참조를 갖는 필드를 모두 반드시 방어적으로 복사해야 합니다.   

### 방어적 복사를 사용하는 불변 클래스, 직렬화할 수 있도록 선언한 것만으로 클래스의 불변식을 깨뜨리는 객체를 만들 수 있음

````java
public final class Period implements Serializable {

    private Date start;
    private Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }

    // 수정자 접근자 - 필드의 방어적 복사본을 반환한다
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        // 가변적 요소들을 방어적으로 복사한다
        start = new Date(start.getTime());
        end = new Date(end.getTime());

        // 불변식을 만족하는지 검사한다
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        }
    }
}
````

## 정리

readObject 메소드를 작성할 때는 언제나 public 생성자를 작성하는 자세로 임해야 합니다.    
readObject는 어떤 바이트 스트림이 넘어오더라도 유효한 인스턴스를 만들어내야 합니다.     
바이트 스트림이 진짜 직렬화된 인스턴스라고 가정해서는 안됩니다.        
readObject 메소드를 작성하는 지침을 요약해보면 다음과 같습니다.    
1. private이어야 하는 객체 참조 필드는 각 필드가 가리키는 객체를 방어적으로 복사하라.
불변 클래스 내의 가변 요소가 여기 속한다.
2. 모든 불변식을 검사하여 어긋나는 게 발견되면 InvalidObjectException을 던진다.
방어적 복사 다음에는 반드시 불변식 검사가 뒤따라야 한다.
3. 역직렬화 후 객체 그래프 전체의 유효성을 검사해야 한다면 ObjectInputValidation 인터페이스를 사용하라.
4. 직접적이든 간접적이든 재정의할 수 있는 메소드는 호출하지 말자.

[아이템 88 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item88)     