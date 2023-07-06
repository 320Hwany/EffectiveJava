# 아이템 33 - 타입 안전 이종 컨테이너를 고려하라

제네릭은 `Set<E>`, `Map<K,V>` 등의 컬렉션과 `ThreadLocal<T>`, `AtomicReference<T>` 등의   
단일 원소 컨테이너에도 흔히 사용됩니다. 이런 모든 쓰임에서 매개변수화 되는 대상은 컨테이너 자신입니다.   
따라서 하나의 컨테이너에서 매개변수화 할 수 있는 타입의 수가 제한됩니다.   
이는 컨테이너의 일반적인 용도에 맞게 설계된 것이기 때문에 문제될 것은 없습니다.   
하지만 더 유연한 수단이 필요할 때가 있는데 예를들어 데이터베이스의 행은 임의 개수의 열을 가질 수 있는데  
모두 열을 타입 안전하게 이용하려고 할 때입니다.   
이때 컨테이너 대신 키를 매개변수화 한 다음 컨테이너에 값을 넣거나 뺄 때 매개변수화 한 키를 함께 제공하면 됩니다.   
이러한 설계 방식을 타입 안전 이종 컨테이너 패턴이라고 합니다.    

## Favorites - 타입 안전 이종 컨테이너 패턴

````
public class Favorites {

    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
````

이 Map은 키와 값 사이의 타입 관계를 보증하지 않습니다.    
하지만 getFavorite 메소드에서 이 관계를 되살릴 수 있기 때문에 큰 상관이 없습니다.   
getFavorite의 구현은 Class의 cast 메소드를 사용해 이 객체 참조를 Class 객체가 카리키는 타입으로   
동적 형변환하면 됩니다. 이렇게 유연하게 사용할 수 있습니다.    

## 구현시 제약 사항

위와 같이 Favorites 클래스에는 알아두어야 할 제약이 2가지 있습니다.   
첫 번째는 악의적인 클라이언트가 Class 객체를 제네릭이 아닌 로타입으로 넘기면 인스턴스의 타입 안전성이   
쉽게 깨집니다. 이때 putFavorite의 instance를 다음과 같이 동적 형변환 해주면 피할 수 있습니다.    
````
public <T> void putFavorite(Class<T> type, T instance) {
    favorites.put(Objects.requireNonNull(type), instance);
}
````

두 번째는 실체화 불가 타입에는 사용할 수 없다는 점입니다.  
String, String[]은 저장할 수 있지만 `List<String>`은 저장할 수 없다는 것입니다.   
슈퍼 타입 토큰이라는 우회 방법이 존재하지만 두 번째 제약에 대한 완벽한 우회는 없습니다.    

## 정리

컬렉션 API로 대표되는 일반적인 제네릭 형태에서는 한 컨테이너가 다룰 수 있는 타입 매개변수의 수가 고정되어 있습니다.    
하지만 컨테이너 자체가 아닌 키를 타입 매개변수로 바꾸면 이런 제약이 없는 타입 안전 이종 컨테이너를 만들 수 있습니다.    
타입 안전 이종 컨테이너는 Class를 키로 쓰며 이런 식으로 쓰이는 Class 객체를 타입 토큰이라고 합니다.   

[아이템 33 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item33)                                                 
[아이템 33 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item33)             