# 아이템 28 - 배열보다는 리스트를 사용하라

배열과 제네릭 타입에는 중요한 차이가 2가지 있습니다.   
첫 번째는 배열은 공변이고 리스트는 불공변이라는 것입니다.   
Sub가 Super의 하위 타입이라면 배열 Sub[]는 배열 Super[]의 하위 타입이 됩니다.   
서로 다른 타입 Type1, Type2가 있을 대 List<Type1>, List<Type2>는 서로 하위 타입도 아니고   
상위 타입도 아닙니다. 배열이 더 유연한 것 같지만 사실 큰 문제가 있습니다.   

### 런타임 시에 오류가 발생하는 배열

````
@Test
@DisplayName("배열은 공변이어서 런타임시에 오류가 발생합니다")
void test1() {
    // given
    Object[] objectArray = new Long[1];

    // expected
    assertThatThrownBy(() -> objectArray[0] = "타입이 달라 런타임시에 오류가 발생합니다")
            .isInstanceOf(ArrayStoreException.class);
}
````

Object 배열에 String 타입을 넣어도 컴파일 시에는 오류가 발생하지 않습니다.   
하지만 런타임 시에 ArrayStoreException이 발생합니다.    

### 컴파일 시에 오류가 발생하는 리스트
````
@Test
@DisplayName("리스트는 불공변이어서 컴파일시에 오류가 발생합니다")
void test1() {
    // List<Object> ol = new ArrayList<Long>(); // 호환되지 않는 타입이다
}
````

제네릭은 타입 정보가 런타임에는 소거됩니다. 원소 타입을 컴파일 타임에만 검사하며   
런타임에는 알 수조차 없습니다.   

## 제네릭 배열은 만들지 못한다

````
List<String>[] strings = new List<String>[1];
````

위와 같이 제네릭 배열을 만들면 컴파일 오류가 발생합니다.   
제네릭 배열을 만들지 못하게 막은 이유는 타입이 안전하지 않기 때문입니다.   
컴파일 시에 타입 안전성을 보장하기 위해 만든 제네릭이 배열과 결합하면 안전성을 보장할 수 없습니다.   

## 정리

배열과 제네릭은 매우 다른 타입의 규칙이 적용됩니다. 배열은 공변이고 실체화되는 반면   
제네릭은 불공변이고 타입 정보가 소거됩니다. 제네릭을 사용하면 타입을 컴파일 시점에 확인하고   
런타임 시점에는 소거합니다. 배열과 제네릭은 반대이기 때문에 섞어 쓰기란 쉽지 않습니다.   
둘을 섞어 사용하기보다는 컴파일 오류나 경고를 만나면 배열을 리스트로 대체하는 방법을 고려해야 합니다.     

[아이템 28 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item28)                   
[아이템 28 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item28)       