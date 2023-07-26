package effective.chapter9.item65;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {

    @Test
    @DisplayName("리플렉션으로 생성하고 인터페이스로 참조해 활용한다")
    void test1() {
        // given
        String[] args = new String[]{"java.util.HashSet", "hello", "world"};

        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("클래스를 찾을 수 없습니다.");
        }

        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("매개변수 없는 생성자를 찾을 수 없습니다.");
        }

        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fatalError("생성자에 접근할 수 없습니다");
        } catch (InstantiationException e) {
            fatalError("클래스를 인스턴스화할 수 없습니다.");
        } catch (InvocationTargetException e) {
            fatalError("생성자가 예외를 던졌습니다: " + e.getCause());
        } catch (ClassCastException e) {
            fatalError("Set을 구현하지 않은 클래스입니다.");
        }

        s.addAll(Arrays.asList(args).subList(1, args.length));
        assertThat(s.size()).isEqualTo(2);
        assertThat(s.contains("hello")).isTrue();
        assertThat(s.contains("world")).isTrue();
    }

    private static void fatalError(String msg) {
        System.out.println(msg);
        System.exit(1);
    }
}
