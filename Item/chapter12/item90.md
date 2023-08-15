# 아이템 90 - 직렬화된 인스턴스 대신 직렬화 프록시 사용을 고려하라

Serializable을 구현하기로 결정한 순간 언어의 정상 메커니즘인 생성자 이외의 방법으로 인스턴스를   
생성할 수 있게 됩니다. 버그와 보안 문제가 일어날 가능성이 커진다는 뜻입니다.    
하지만 직렬화 프록시 패턴을 사용하면 이 위험을 크게 줄여줄 수 있습니다.     

직렬화 프록시 패턴은 먼저 바깥 클래스의 논리적 상태를 정밀하게 표현하는 중첩 클래스를 설계해    
private static 으로 선언합니다. 이 중첩 클래스가 바로 바깥 클래스의 직렬화 프록시입니다.    
중첩 클래스의 생성자는 단 하나여야 하며 바깥 클래스를 매개변수로 받아야 합니다.     
이 생성자는 단순히 인수로 넘어온 인스턴스의 데이터를 복사합니다.    

### Period

````java
public class Period implements Serializable {

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

    private Object writeReplace() {
        return new SerializationProxy(this);
    }
    
    private static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;

        public SerializationProxy(final Period p) {
            this.start = p.start();
            this.end = p.end();
        }

        private static final long serialVersionUID = 239284274294732L; // 랜덤 값

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("프록시가 필요합니다");
        }
    }
}
````

writeReplace 덕분에 직렬화 시스템은 결코 바깥 클래스의 직렬화된 인스턴스를 생성해낼 수 없습니다.         
readObject 메소드를 바깥 클래스에 추가하여 불변식을 훼손하고자 하는 시도를 막아낼 수 있습니다.        
또한 바깥 클래스와 논리적으로 동일한 인스턴스를 반환하는 readResolve 메소드를 SerializationProxy 클래스에    
추가하여 역직렬화 시에 직렬화 시스템이 직렬화 프록시를 다시 바깥 클래스의 인스턴스로 반환하게 해줍니다.     

## 정리

제 3자가 확장할 수 없는 클래스라면 가능한 한 직렬화 프록시 패턴을 사용해야 합니다.
이 패턴은 아마도 중요한 불변식을 안정적으로 직렬화해주는 가장 쉬운 방법일 것입니다.        

[아이템 90 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item90)        