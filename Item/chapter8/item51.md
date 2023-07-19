# 아이템 51 - 메소드 시그니처를 신중히 설계하라

1. 메소드 이름을 신중히 짓자
항상 표준 명명 규칙에 따라야 합니다. 같은 패키지에 속한 다른 이름들과 일관되게 짓는 것이 최우선입니다.        
개발자 커뮤니티에서 널리 받아들여지는 이름을 사용하고 긴 이름은 피해야 합니다.       
자바 라이브러리의 API 가이드를 참고할 수도 있습니다.       

2. 편의 메소드는 너무 많이 만들지 말자
메소드가 너무 많은 클래스는 익히고, 사용하고, 문서화하고, 테스트하고, 유지보수하기 어렵습니다.      
클래스나 인터페이스는 자신의 각 기능을 완벽히 수행하는 메소드로 제공해야 합니다.       
아주 자주 쓰일 경우에만 별도의 약칭 메소드를 두고 확신이 서지 않으면 만들지 말아야 합니다.       

3. 매개변수 목록은 짧게 유지하자
매개변수 목록은 4개이하가 좋습니다. IDE를 사용하면 수고를 덜 수 있지만 여전히 매개변수 수는      
적은 쪽이 훨씬 낫습니다. 특히 같은 타입의 매개변수 여러 개가 연달아 나오는 경우에는 주의해야 합니다.      
사용자가 매개변수 순서를 기억하기 어렵고 실수로 순서를 바꿔 입력해도 그대로 컴파일되어 실행되기 때문입니다.

## 매개변수 목록을 짧게 유지하기 위한 기술 3가지

첫 번째로 여러 메소드로 쪼개는 경우가 있습니다.   
List 인터페이스가 좋은 예시인데 리스트에서 주어진 원소의 인덱스를 찾아야 하는데 전체 리스트가 아니라    
지정된 범위의 부분리스트에서의 인덱스를 찾는 경우를 생각해보겠습니다.     
이 기능을 하나의 메소드로 구현하려면 '부분 리스트 시작', '부분 리스트의 끝', '찾을 원소'까지 3개의 매개변수가 필요합니다.     

### 매개변수가 3개인 메소드 - 가능하면 줄이자

````java
public int findIndexBad(int from, int to, String value) {
    List<String> subList = strings.subList(from, to);
    if (subList.contains(value)) {
        return strings.indexOf(value);
    }
    return -1;
}
````

### 2개의 메소드로 분리했다

````java
public List<String> getSubList(int from, int to) {
    return strings.subList(from, to);
}

public int findIndexBetter(List<String> subList, String value) {
    return subList.indexOf(value);
}
````

2번째 방법은 매개변수 여러 개를 묶어주는 도우미 클래스를 만드는 것입니다.       

### 매개변수 목록이 긴 update 메소드

````java
public void update1(String name, int age, float height, float weight) {
    this.name = name;
    this.age = age;
    this.height = height;
    this.weight = weight;
}
````

### 매개변수 여러 개를 묶어주는 도우미 클래스를 사용한 update 메소드

````java
public void update2(UpdateDto dto) {
    this.name = dto.getName();
    this.age = dto.getAge();
    this.height = dto.getHeight();
    this.weight = dto.getWeight();
}
````

3번째 방법은 위의 2가지 기법을 혼합해서 객체 생성에 사용한 빌더 패턴을 메소드 호출에 응용하는 것입니다.     


## 정리

메소드 이름을 신중히 짓고, 편의 메소드를 너무 많이 만들지 말고, 매개변수 목록은 짧게 유지해야 합니다.   
매개변수 목록을 짧게 유지하기 위해서는 메소드를 분리하거나, 매개변수 여러 개를 묶어주는 도우미 클래스를 활용할 수 있습니다.   
이외에도 매개변수 타입으로는 클래스보다 인터페이스를 사용하는 것이 더 나으며 확실한 경우가 아니라면 boolean 보다는   
원소 2개짜리 열거 타입을 사용하는 것이 낫습니다.         

[아이템 51 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter8/item51)                                                                                     
[아이템 51 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter8/item51)         

