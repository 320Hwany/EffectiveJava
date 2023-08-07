# 아이템 83 - 지연 초기화는 신중히 사용하라

지연 초기화는 필드의 초기화 시점을 그 값이 처음 필요할 때까지 늦추는 기법입니다.     
다른 모든 최적화와 마찬가지로 지연 초기화는 필요할 때까지는 하지말아야 합니다.   
지연 초기화는 양날의 검으로 클래스 혹은 인스턴스 생성 시의 초기화 비용은 줄지만 그 대신   
지연 초기화하는 필드에 접근하는 비용은 커집니다.    

그럼에도 지연 초기화가 필요할 때가 있는데 해당 클래스의 인스턴스 중 그 필드를 사용하는 인스턴스의 비율이       
낮은 반면 그 필드를 초기화하는 비용이 크다면 지연 초기화가 제 역할을 해줄 것입니다.    

## 필드를 초기화 하는 5가지 방식 

````java
public class LazyInitializationClass {

    // 인스턴스 필드를 초기화하는 일반적인 방법
    private final Integer field1 = 10;
    
    public Integer getField1() {
        return field1;
    }

    // 인스턴스 필드의 지연 초기화 - synchronized 접근자 방식
    private Integer field2;

    public synchronized Integer getField2() {
        if (field2 == null) {
            field2 = 10;
        }
        return field2;
    }


    // 정적 필드용 지연 초기화 홀더 클래스 관용구
    static class FieldHolder {
        static final Integer field3 = 10;
    }

    public static Integer getField3() {
        return FieldHolder.field3;
    }


    // 인스턴스 필드 지연 초기화용 이중검사 관용구
    private volatile Integer field4;

    public Integer getField4() {
        Integer result = field4;
        if (result != null) {
            return result;  // 첫 번째 검사 (락 사용 안함)
        }

        synchronized (this) {
            if (field4 == null) {
                field4 = 10; // 두 번째 검사 (락 사용)
            }
            return field4;
        }
    }
    
    // 단일 검사 관용구 - 초기화가 중복해서 일어날 수 있다
    private volatile Integer field5;

    public Integer getField5() {
        Integer result = field5;
        if (result == null) {
            return field5 = result = 10;  
        }
        return result;
    }
}
````

성능 때문에 정적 필드를 지연 초기화해야 한다면 지연 초기화 홀더 클래스 관용구를 사용해야 합니다(3)        
이 관용구의 멋진 점은 getField3 메소드가 필드에 접근하면서 동기화를 전혀 하지 않으니 성능이 느려질 거리가   
전혀 없다는 점입니다.    

성능 때문에 인스턴스 필드를 지연 초기화해야 한다면 이중검사 관용구를 사용해야 합니다(4)     
이 관용구는 초기화된 필드에 접근할 때의 동기화 비용을 없애줍니다.        
필드의 값을 두 번 검사하는 방식으로 한 번은 동기화 없이 검사하고 두 번째는 동기화하여 검사합니다.     

## 정리

대부분의 필드는 지연시키지 말고 곧바로 초기화해야 합니다. 성능 때문에 혹은 위험한 초기화 순환을 막기 위해          
꼭 지연 초기화를 써야 한다면 올바른 지연 초기화 기법을 사용해야 합니다.     
인스턴스 필드에는 이중검사 관용구를 정적 필드에는 지연 초기화 홀더 클래스 관용구를 사용해야 합니다.          

[아이템 83 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter11/item83)                                                                                                      
[아이템 83 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter11/item83)                

