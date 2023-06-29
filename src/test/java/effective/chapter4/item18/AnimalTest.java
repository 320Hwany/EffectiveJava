package effective.chapter4.item18;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    @DisplayName("Animal을 상속하는 대신 컴포지션을 활용합니다")
    void test() {
        // given
        Animal animal = new Animal("이름", 5);
        Tiger tiger = new Tiger(animal);

        // when
        tiger.cry();
        Animal getAnimal = tiger.getAnimal();

        // then
        assertThat(animal).isSameAs(getAnimal);
    }
}