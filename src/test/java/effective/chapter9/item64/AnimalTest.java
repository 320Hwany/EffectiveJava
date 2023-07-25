package effective.chapter9.item64;

import effective.chapter9.item64.Animal;
import effective.chapter9.item64.Eagle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AnimalTest {

    @Test
    @DisplayName("객체는 클래스가 아닌 인터페이스로 참조하라")
    void test1() {
        // given
        Animal animal = new Eagle();

        // when
        String cry = animal.cry();
        String eat = animal.eat();

        // then
        assertThat(cry).isEqualTo("Eagle cry");
        assertThat(eat).isEqualTo("Eagle eat");
    }

    @Test
    @DisplayName("클래스 타입을 직접 사용하는 경우는 추가 메소드를 꼭 사용해야 하는 경우로 최소화 - 남발하면 안됨")
    void test2() {
        // given
        Eagle animal = new Eagle();

        // when
        String cry = animal.cry();
        String eat = animal.eat();
        String fly = animal.fly(); // 구체 클래스만 제공하는 기능

        // then
        assertThat(cry).isEqualTo("Eagle cry");
        assertThat(eat).isEqualTo("Eagle eat");
        assertThat(fly).isEqualTo("Eagle fly");
    }
}
