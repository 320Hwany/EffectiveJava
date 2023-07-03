# 아이템 27 - 비검사 경고를 제거하라

제네릭을 사용하기 시작하면 수많은 컴파일러 경고를 보게 될 것입니다 (물론 좋은 에러입니다)     
비검사 형변환 경고, 비검사 메소드 호출 경고, 비검사 매개변수화 가변인수 타입 경고, 비검사 변환 경고 등입니다.         
대부분의 비검사 경고는 쉽게 제거할 수 있습니다.   

다음과 같이 작성하면 컴파일러가 경고를 보냅니다.     
````
Set<String> strings = new HashSet();
````

자바 7 이후부터는 다음과 같이 수정해도 컴파일러가 올바른 실제 타입 매개변수를 추론해줍니다.     
````
Set<String> strings = new HashSet<>();
````

할 수 있는 한 모든 비검사 경고를 제거해야 합니다. 모두 제거한다면 그 코드는 타입 안전성이 보장됩니다.                    
경고를 제거할 수는 없지만 타입 안전하다고 확신할 수 있다면 @SuppressWarning("unchecked") 애너테이션을        
달아 경고를 숨기는 방법도 생각해볼 수 있습니다.               
@SuppressWarnings 애너테이션은 개별 지역변수 선언부터 클래스 전체까지 어떤 선언에도 달 수 있습니다.    
하지만 @SuppressWarnings 애너테이션은 항상 가능한 한 좁은 범위에 적용해야 합니다.     

다음은 ArrayList에서 가져온 toArray 메소드를 변형한 것입니다.   

### toArray

````
public <T> T[] toArray(T[] a) {
    if (a.length < size)  
        return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        
    System.arraycopy(elementData, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
````

비검사 경고를 보내는 데 타입 안정성이 확실하다면 @SuppressWarnings를 사용할 수 있습니다.      
이때 그 경고를 무시해도 안전한 이유를 항상 주석으로 남겨야 합니다.   

````
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        @SuppressWarnings("unchecked")  // 생성한 배열과 매개변수로 받은 배열의 타입이 T[]로 같으므로
        // 올바른 형변환이다
        T[] result = (T[]) Arrays.copyOf(elementData, size, a.getClass());
        return result;
    }
    System.arraycopy(elementData, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
````

## 정리

비검사 경고는 중요하니 무시하면 안됩니다. 모든 비검사 경고는 ClassCastException을 일으킬 수 있는   
잠재적 가능성을 뜻하기 때문에 최선을 다해 제거해야 합니다.   
경고를 없앨 방법을 찾지 못했다면 그 코드가 타입 안전함을 증명하고 가능한 한 범위를 좁혀   
@SuppressWarnings("unchecked") 애너테이션으로 경고를 숨겨야 합니다.   
그런 다음 경고를 숨기기로 한 근거를 주석으로 남겨야 합니다.       

[아이템 27 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item27) 