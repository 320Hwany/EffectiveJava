package effective.chapter4.item24;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NestedClassTest {

    @Test
    @DisplayName("비정적 멤버 클래스의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결됩니다")
    void test1() {
        // given
        Parent1 parent = new Parent1("부모 이름", 50);
        Parent1.Child child = parent.new Child("자식 이름", 20);

        // when
        String parentName = child.getParentName();

        // then
        assertThat(parentName).isEqualTo("부모 이름");
    }

    @Test
    @DisplayName("정적 멤버 클래스는 바깥 인스턴스 없이 생성할 수 있습니다")
    void test2() {
        // given
        Parent2.Child child = new Parent2.Child("자식 이름", 20);

        // expected
        assertThat(child).isNotNull();
    }

    @Test
    @DisplayName("Calculator 안에 Operation 정적 멤버 클래스를 정의하여 사용합니다")
    void test3() {
        // given
        Calculator.Operation operation = new Calculator.Operation();

        // when
        int sum = operation.plus(10, 20);

        // then
        assertThat(sum).isEqualTo(30);
    }

    @Test
    @DisplayName("익명 클래스를 사용하여 Member의 메소드를 재정의할 수 있습니다")
    void test4() {
        // given
        Member1 member = new Member1() {

            private final String name = "이름";
            public String myNameIs() {
                return name;
            }
        };

        // when
        String returnName = member.myNameIs();
        String hello = member.hello;

        // then
        assertThat(returnName).isEqualTo("이름");
        assertThat(hello).isEqualTo("hello"); // 비정적인 문맥에서 사용될 때 바깥 클래스의 인스턴스 참조 가능
    }

    @Test
    @DisplayName("메소드 안에 지역 클래스를 정의하여 사용할 수 있습니다")
    void test5() {
        // given
        Member2 member2 = new Member2();

        // when
        String childName = member2.getChildName();

        // then
        assertThat(childName).isEqualTo("자식 이름");
    }
}
