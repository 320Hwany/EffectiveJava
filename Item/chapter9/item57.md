# 아이템 57 - 지역변수의 범위를 최소화하라

지역변수의 유효 범위를 최소로 줄이면 코드 가독성과 유지보수성이 높아지고 오류 가능성이 낮아집니다.     
지역변수의 범위를 줄이는 가장 강력한 기법은 '가장 처음 쓰일 때 선언하기'입니다.       
사용하려면 멀었는데 미리 선언부터 해두면 코드가 어수선해져 가독성이 떨어집니다.    
지역변수를 생각 없이 선언하다 보면 변수가 쓰이는 범위보다 너무 앞서 선언하거나 다 쓴 뒤에도   
여전히 살아 있게 되기 쉽습니다.     
또한 '거의 모든 지역변수는 선언과 동시에 초기화'해야 합니다.       

## 반복문에서의 지역변수

### while 문으로 사용하면 선언한 i의 유효범위가 넓어집니다 - 이 경우에는 for 문을 사용하자

````java
@Test
void test1() {
    // given
    Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

    // when
    List<String> hobbies = member.getHobbies();

    // then
    int i = 0;
    while (i < hobbies.size()) {
        assertThat(hobbies.contains(hobbies.get(i))).isTrue();
        i++;
    }
    assertThat(i).isEqualTo(5);
}
````
위와 같이 반복문이 끝났는데도 i가 살아있음을 확인할 수 있습니다.       
i는 더이상 사용될 일이 거의 없기 때문에 이 경우에는 for 문으로 사용하는 것이 더 좋은 방법입니다.         

### for 문으로 사용하면 선언한 i가 반복문안에서만 유효합니다

````java
@Test
void test2() {
    // given
    Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

    // when
    List<String> hobbies = member.getHobbies();

    // then
    for (int i = 0; i < hobbies.size(); i++) {
        assertThat(hobbies.contains(hobbies.get(i))).isTrue();
    }
}
````
for 문에서는 선언한 i가 반복문 안에서만 유효합니다.     

### 컬렉션이나 배열을 순회하는 권장 관용구

````java
@Test
void test3() {
    // given
    Member member = new Member("name", 20, List.of("hobby1", "hobby2", "hobby3", "hobby4", "hobby5"));

    // when
    List<String> hobbies = member.getHobbies();

    // then
    for (String hobby : hobbies) {
        assertThat(hobbies.contains(hobby)).isTrue();
    }
}
````
다음으로 반복자를 사용할 필요가 없다면 for-each 문을 사용할 수 있습니다.       

## 정리

지역변수의 범위를 줄이기 위해 '가장 처음 쓰일 때 선언하기', '거의 모든 지역변수는 선언과 동시에 초기화하기'를 지키도록     
해야합니다. 또한 while 문 대신 for 문을 사용할 수 있으며 for-each 문도 적절하게 사용하면 좋습니다.     
또한 메소드를 작게 유지하고 한가지 기능에 집중하여 지역변수의 유효범위를 줄일 수도 있습니다.       

[아이템 57 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item57)                                                                                                 
[아이템 57 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item57)       

