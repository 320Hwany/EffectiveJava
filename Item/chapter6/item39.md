# 아이템 39 - 명명 패턴보다 애너테이션을 사용하라

전통적으로 도구나 프레임워크가 특별히 다뤄야 할 프로그램 요소에는 딱 구분되는 명명 패턴을 적용해왔습니다.   
하지만 명명패턴은 오타가 나면 안되고 올바름 프로그램 요소에서만 사용되리라고 보장할 방법이 없으며    
프로그램 요소를 매개변수로 전달할 마땅한 방법이 없다는 단점이 있습니다.    

애너테이션은 이 모든 문제를 해결해주는 멋진 개념으로 JUnit도 버전 4부터 도입하였습니다.         

## CustomTest - 마커 애너테이션
````
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomTest {
}
````

@Retention, @Target과 같이 애너테이션 선언에 다는 애너테이션을 메타 애너테이션이라고 합니다.   
@Retention(RetentionPolicy.RUNTIME)은 런타임에도 유지되어야 한다는 것을 표시하며   
@Target(ElementType.METHOD)은 반드시 메소드 선언에서만 사용되어야 한다는 것을 알려줍니다.    
이와 같은 애너테이션을 아무 매개변수 없이 단순히 대상에 마킹한다는 뜻에서 "마커 애너테이션"이라고 합니다.     

### Sample1 - 마커 애너테이션을 사용한 프로그램
````
public class Sample1 {

    @CustomTest
    public static void m1() {}

    public static void m2() {}

    @CustomTest
    public static void m3() {
        throw new RuntimeException("실패");
    }

    public static void m4() {}

    @CustomTest
    public void m5() {}

    public static void m6() {}

    @CustomTest
    public static void m7() {
        throw new RuntimeException("실패");
    }

    public static void m8() {}
}
````

대상 코드의 의미는 그대로 둔 채 그 애너테이션에 관심있는 도구에서 특별한 처리를 할 기회를 줍니다.       

### test1 - 마커 애너테이션을 처리하는 프로그램

테스트로 원하는 메소드에 잘 적용되었는지 확인합니다.     

````
@Test
@DisplayName("마커 애너테이션을 처리하는 프로그램")
void test1() {
    // given
    int tests = 0;
    int passed = 0;
    Class<?> testClass = Sample1.class;

    // when
    for (Method m : testClass.getDeclaredMethods()) {
        if (m.isAnnotationPresent(CustomTest.class)) {
            tests ++;
            try {
                m.invoke(null);
                passed++;
            } catch (InvocationTargetException wrappedExc) { // 테스트 메소드에서 예외 발생
                Throwable exc = wrappedExc.getCause();
                System.out.println(m + " 실패: " + exc);
            } catch (Exception exc) {
                System.out.println("잘못 사용한 @MyAnnotationTest: " + m);
            }
        }
    }
    System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

    // then
    assertThat(tests).isEqualTo(4);
    assertThat(passed).isEqualTo(1);
    assertThat(tests - passed).isEqualTo(3);
}
````

## Sample2 - 매개변수 하나짜리 애너테이션을 사용한 프로그램

테스트 중에 예외가 발생하면 성공하는 테스트도 만들 필요가 있습니다.   
이 경우에는 매개변수를 받아서 처리할 수 있습니다.      

````
public class Sample2 {

    @ExceptionTest1(ArithmeticException.class)
    public static void m1() { // 성공해야 한다
        int i = 0;
        i = i / i;
    }

    @ExceptionTest1(ArithmeticException.class)
    public static void m2() { // 실패해야 한다 (다른 예외 발생)
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest1(ArithmeticException.class)
    public static void m3() {} // 실패해야 한다 (예외가 발생하지 않음)
}
````

### ExceptionTest2 -  매개변수 하나를 받는 애너테이션 타입

````
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest1 { 
    Class<? extends Throwable> value();
}
````

### test2 - 매개변수 하나짜리 애너테이션을 사용한 프로그램

````
@Test
@DisplayName("매개변수 하나짜리 애너테이션을 사용한 프로그램")
void test2() {
    // given
    int tests = 0;
    int passed = 0;
    Class<?> testClass = Sample2.class;

    // when
    for (Method m : testClass.getDeclaredMethods()) {
        if (m.isAnnotationPresent(ExceptionTest1.class)) {
            tests ++;
            try {
                m.invoke(null);
                System.out.printf("테스트  %s 실패: 예외를 던지지 않음%n", m);
            } catch (InvocationTargetException wrappedExc) {
                Throwable exc = wrappedExc.getCause();
                Class<? extends Throwable> excType =
                        m.getAnnotation(ExceptionTest1.class).value();
                if (excType.isInstance(exc)) {
                    passed++;
                } else {
                    System.out.printf("테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s%n", m, excType.getName(), exc);
                }
            } catch(Exception exc){
                System.out.print("잘못 사용한 @ExceptionTest1: " + m);
            }
        }
    }
    System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

    // then
    assertThat(tests).isEqualTo(3);
    assertThat(passed).isEqualTo(1);
    assertThat(tests - passed).isEqualTo(2);
}
````

## 정리

애너테이션은 명명패턴의 단점들을 해결한 방법입니다. 자바에는 이미 많은 종류의 애너테이션 타입을 정의해두었습니다.     
명명패턴보다 훨씬 장점이 많기 때문에 적극적으로 사용해야 합니다.        
위 예시에서 마커 애너테이션, 매개변수 하나를 받는 애너테이션의 예시로 테스트가 언제 성공하는지를 살펴봤습니다.   
매개변수 같은 경우에는 배열 매개변수를 받거나 반복 가능한 애너테이션 타입을 이용해 여러 개의 매개변수도       
받을 수 있습니다. 이에 대한 예제 코드와 테스트 코드도 아래 링크에 작성해두었습니다.      

[아이템 39 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item39)                                                         
[아이템 39 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item39)       