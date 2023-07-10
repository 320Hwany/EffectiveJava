package effective.chapter6.item39;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class MyAnnotationTest {

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

    @Test
    @DisplayName("배열 매개변수를 받는 애너테이션을 사용하는 코드")
    void test3() {
        // given
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Sample3.class;

        // when
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest2.class)) {
                tests ++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트  %s 실패: 예외를 던지지 않음%n", m);
                } catch (Throwable wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    int oldPassed = passed;
                    Class<? extends Throwable>[] excTypes =
                            m.getAnnotation(ExceptionTest2.class).value();
                    for (Class<? extends Throwable> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패: %s %n", m, exc);
                    }
                }
            }
        }
        System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

        // then
        assertThat(tests).isEqualTo(1);
        assertThat(passed).isEqualTo(1);
        assertThat(tests - passed).isEqualTo(0);
    }

    @Test
    @DisplayName("반복 가능 애너테이션 다루기")
    void test4() {
        // given
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Sample4.class;

        // when
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest3.class) ||
                    m.isAnnotationPresent(ExceptionTestContainer.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트  %s 실패: 예외를 던지지 않음%n", m);
                } catch (Throwable wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    int oldPassed = passed;
                    ExceptionTest3[] excTests =
                            m.getAnnotationsByType(ExceptionTest3.class);
                    for (ExceptionTest3 excTest : excTests) {
                        if (excTest.value().isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패: %s %n", m, exc);
                    }
                }
            }
        }
        System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);

        // then
        assertThat(tests).isEqualTo(1);
        assertThat(passed).isEqualTo(1);
        assertThat(tests - passed).isEqualTo(0);
    }
}
