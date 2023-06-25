package effective.chapter2.item3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("싱글톤 - public static 멤버가 final 필드인 방식")
    void test1() {
        // given
        Member1 member1 = Member1.INSTANCE;
        Member1 member2 = Member1.INSTANCE;

        // expected
        assertThat(member1).isEqualTo(member2);
    }

    @Test
    @DisplayName("싱글톤 - 정적 팩토리 메소드를 public static 멤버로 제공한다")
    void test2() {
        // given
        Member2 member1 = Member2.getInstance();
        Member2 member2 = Member2.getInstance();

        // expected
        assertThat(member1).isEqualTo(member2);
    }

    @Test
    @DisplayName("싱글톤 - 원소가 하나뿐인 열거 타입 사용")
    void test3() {
        // given
        Member3 member1 = Member3.INSTANCE;
        Member3 member2 = Member3.INSTANCE;

        String name = Member3.INSTANCE.getName();
        int age = Member3.INSTANCE.getAge();

        // expected
        assertThat(member1).isEqualTo(member2);
        assertThat(name).isEqualTo("이름");
        assertThat(age).isEqualTo(20);
    }

    @Test
    @DisplayName("싱글톤 - 원소가 하나뿐인 열거 타입 사용")
    void test4() {
        // given
        Member4 member1 = Member4.INSTANCE;
        Member4 member2 = Member4.INSTANCE;

        String name = Member4.name;
        int age = Member4.age;

        // expected
        assertThat(member1).isEqualTo(member2);
        assertThat(name).isEqualTo("이름");
        assertThat(age).isEqualTo(20);
    }
}
