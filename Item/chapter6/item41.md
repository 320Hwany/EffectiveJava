# 아이템 41 - 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라

아무 메소드도 담고 있지 않고 단지 자신을 구현하는 클래스가 특정 속성을 가짐을 표시해주는 인터페이스를   
마커 인터페이스라고 합니다. 대표적인 예로 Serializable이 있습니다.      

### Serializable
````
public interface Serializable {
}
````

Serializable은 자신을 구현한 클래스의 인스턴스는 ObjectOutputStream을 통해 쓸 수 있다고  
즉 직렬화할 수 있다고 알려줍니다.      
마커 인터페이스는 어엿한 타입이기 때문에 구현한 클래스의 인스턴스들을 구분하여 오류가 있다면 컴파일 타임에    
잡아낼 수 있습니다. 이러한 점에서는 마커 애노테이션보다 더 나은 방법입니다.      

### 컴파일타임 오류 검출 이점을 살리지 못함

Serializable은 objectOutputStream.writeObject의 메소드가 매개변수로 Object를 받습니다.  
즉 직렬화할 수 없는 객체를 넘겨도 런타임에야 문제를 확인하여 마커 인터페이스의 이점을 잘 살리지 못하였습니다.      

````
@Test
@DisplayName("마커 인터페이스를 사용하는 주된 이유는 컴파일타임 오류 검출인데 이점을 살리지 못한 케이스입니다")
void test2() throws IOException {
    File f= new File("test.txt");
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));

    assertThatThrownBy(() -> objectOutputStream.writeObject(new Member("name", 20)))
            .isInstanceOf(NotSerializableException.class);
}
````

마커 인터페이스가 마커 애노테이션보다 나은 점이 하나 더 있는데 적용 대상을 더 정밀하게 지정할 수 있다는 것입니다.       
특정 인터페이스를 구현한 클래스에만 적용하고 싶은 마커가 있다면 이 마커를 인터페이스로 정의했다면 그냥 마킹하고 싶은   
클래스에서만 그 인터페이스를 구현하면 된다. 그러면 마킹된 타입은 자동으로 그 인터페이스의 하위 타입임이 보장됩니다.    
마커 애노테이션의 경우에는 @Target으로 적용대상을 지정하기 때문에 마커 인터페이스보다는 정밀하지 못합니다.   

## 정리

마커 인터페이스와 마커 애노테이션은 각자의 쓰임이 있습니다. 새로 추가하는 메소드 없이 단지 타입 정의가 목적이라면   
마커 인터페이스를 선택하는 것이 나을 수 있습니다. 클래스나 인터페이스 외의 프로그램 요소에 마킹해야 하거나   
애노테이션을 적극 활용하는 프레임워크의 일부로 그 마커를 편입시키고자 한다면 마커 애노테이션이 더 나은 방법입니다.   

[아이템 41 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item41)                                                                
[아이템 41 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item41)           
