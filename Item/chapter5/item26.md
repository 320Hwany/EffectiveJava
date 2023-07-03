# 아이템 26 - 로 타입은 사용하지 말라

각각의 제네릭 타입은 일련의 매개변수화 타입을 정의합니다.   
먼저 클래스(or 인터페이스) 이름이 나오고 이어서 꺾쇠 괄호 안에 실제 타입 매개변수들을 나열합니다.       
예를들어 List<String>은 원소의 타입이 String인 리스트를 뜻하는 매개변수화 타입입니다.       
이때 제네릭 타입을 하나 정의하면 그에 딸린 로 타입도 함께 정의됩니다.   


## 로 타입

로 타입이란 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않은 것을 말합니다.   
List<E>의 로 타입은 List 입니다. 로 타입은 제네릭이 지원되기 전 코드와의 호환을 위해   
남겨 둔 것이기 때문에 사용을 지양해야합니다.   
오류는 가능한 한 발생 즉시 즉 컴파일할 때 발견하는 것이 좋습니다.   
로 타입을 사용하면 타입을 잘못 넣어도 컴파일 시점에 알지 못하고 런타임 시점에 가서야 알 수 있습니다.   

### 로 타입은 컴파일 시점에 오류를 발견하지 못한다

````
@Test
@DisplayName("로 타입은 타입을 잘못 넣어도 컴파일 시점에 잡을 수 없습니다")
void test1() {
    // given
    List tigers = new ArrayList();
    tigers.add(new Tiger("호랑이 이름"));
    tigers.add(new Lion("사자 이름")); // 실수로 사자를 넣어도 컴파일 타임에 모른다
    
    // when
    Tiger tiger1 = (Tiger) tigers.get(0);
    assertThatThrownBy(() -> {
        Tiger tiger2 = (Tiger) tigers.get(1);
    }).isInstanceOf(ClassCastException.class);
}
````

따라서 타입의 안정성, 표현력을 위해서 제네릭을 사용해야 합니다.     

그렇다면 타입을 알지 못할 때는 로 타입을 사용해야 될까요?    
타입이 확정되지 않았을 경우를 대비하여 비한정적 와일드카드 타입을 제공합니다.   

### 비한정적 와일드카드 타입은 안전하며 유연하다

````
@Test
@DisplayName("비한정적 와일드카드 타입은 안전하고 로 타입은 안전하지 않다")
void test2() {
    // given
    Set<Integer> s1 = Set.of(1, 2, 3);
    Set<Integer> s2 = Set.of(1, 2, 3, 4, 5);
    Set s3 = Set.of(1, 2, 3, "4", "5"); // 타입이 안맞아도 컴파일 단계에서 잡아주지 못한다

    // when
    int result = numElementsInCommon(s1, s2);

    // then
    assertThat(result).isEqualTo(3);
}
````

## 로 타입을 사용해도 되는 경우

예외적으로 로 타입을 사용해도 되는 경우가 2가지 있습니다.    

### Class 리터럴

````
@Test
@DisplayName("로 타입을 사용해도 되는 예외 1 - class 리터럴")
void test3() {
    // 가능
    Class<List> listClass = List.class;
    Class<String[]> aClass = String[].class;
    Class<Integer> integerClass = int.class;

    // 불가능
    // List<String>.class
    // List<?>.class
}
````

### InstanceOf 연산자

런타임에는 제네릭 타입 정보가 지워지므로 instanceOf 연산자는 비한정적 와일드카드 타입    
이외의 매개변수화 타입에는 적용할 수 없습니다.       

````
@Test
@DisplayName("로 타입을 사용해도 되는 예외 2 - instanceOf 연산자")
void test4() {
    // given
    List<String> strings = new ArrayList<>();
    boolean b1 = false;
    boolean b2 = false;

    // when
    if(strings instanceof List){
        b1 = true;
    }

    if (strings instanceof Set) {
        b2 = true;
    }

    // then
    assertThat(b1).isTrue();
    assertThat(b2).isFalse();
}

static int numElementsInCommon(Set<?> s1, Set<?> s2) {
    int result = 0;
    for (Object o1 : s1) {
        if (s2.contains(o1)) {
            result++;
        }
    }
    return result;
}
````

## 정리
 
로 타입을 사용하면 런타임에 예외가 일어날 수 있으니 사용하면 안됩니다.    
Set<Object>는 어떤 타입의 객체도 저장할 수 있는 매개변수화 타입이고    
Set<?> 모종의 타입 객체만 저장할 수 있는 와일드카드 타입입니다.<br> 
Set은 로 타입으로 제네릭 타입이 아닙니다.<br>
Set<Object>, Set<?>은 안전하지만 로 타입 Set은 안전하지 않습니다.        

[아이템 26 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter5/item26)                  
[아이템 26 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter5/item26) 