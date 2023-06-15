package effective.item1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AnimalTest {

    @Test
    @DisplayName("반환 타입의 하위 타입 객체를 반환할 수 있습니다")
    void toAnimal() {
        // given
        Tiger tiger = new Tiger("호랑이", 5);
        Lion lion = new Lion("사자", 3);

        // when
        Animal tigerAnimal = Animal.toAnimal(tiger);
        Animal lionAnimal = Animal.toAnimal(lion);

        // then
        assertThat(tigerAnimal).isInstanceOf(Animal.class);
        assertThat(lionAnimal).isInstanceOf(Animal.class);
    }
}
