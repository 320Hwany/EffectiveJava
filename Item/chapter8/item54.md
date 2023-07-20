# 아이템 54 - null이 아닌 빈 컬렉션이나 배열을 반환하라

컬렉션이나 배열이 비어있다고 null을 반환해서는 안됩니다.           
null을 반환한다면 클라이언트는 이 null 상황을 처리하는 코드를 추가로 작성해야 합니다.       
클라이언트에서 방어 코드를 빼먹으면 오류가 발생할 수 있습니다.      

빈 컨테이너를 할당하는 데도 비용이 드니 null을 반환하는 쪽이 낫다는 주장은 2가지면에 틀립니다.  

1. 성능 분석 결과 이 할당이 성능 저하의 주범이라고 확인되지 않는 한 이 정도의 성능 차이는 신경 쓸 수준이 되지 못합니다.  
2. 빈 컬렉션과 배열은 굳이 새로 할당하지 않고도 반환할 수 있습니다.  

불변 객체는 자유롭게 공유해도 안전하기 때문에 매번 똑같은 빈 '불변' 컬렉션을 반환하는 것입니다.       
예를들어 Collections.emptyList가 그 예시입니다.     
마찬가지로 빈 배열을 미리 할당하여 공유할 수 있습니다.       
하지만 항상 최선인 것은 아니기 때문에 주의가 필요합니다.      

## Stock

````java
public class Stock {

    private List<String> stringInStock = new ArrayList<>();

    // 컬렉션이 비었으면 null을 반환한다 - 따라 하지 말것
    public List<String> getStringsBad() {
        return stringInStock.isEmpty() ? null : new ArrayList<>(stringInStock);
    }

    // 빈 컬렉션을 반환하는 올바른 예
    public List<String> getStringsBetter() {
        return new ArrayList<>(stringInStock);
    }

    // 최적화 - 빈 컬렉션을 매번 새로 할당하지 않도록 함
    public List<String> getStringOptimization() {
        return stringInStock.isEmpty() ? Collections.emptyList()
                : new ArrayList<>(stringInStock);
    }

    // 길이가 0일수도 있는 배열을 반환하는 올바른 방법
    public String[] getStringArray() {
        return stringInStock.toArray(new String[0]);
    }

    // 최적화 - 빈 배열을 매번 새로 할당하지 않도록 함 (항상 최선은 아니다)
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public String[] getStringArrayOptimization() {
        return stringInStock.toArray(EMPTY_STRING_ARRAY);
    }
}
````

## 정리

null이 아닌 빈 배열이나 컬렉션을 반환해야 합니다.          
null을 반환하는 API는 사용하기 어렵고 오류 처리 코드도 늘어납니다.         
그렇다고 성능이 좋은 것도 아닙니다.        

[아이템 54 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item54)                                                                                        
[아이템 54 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item54)          