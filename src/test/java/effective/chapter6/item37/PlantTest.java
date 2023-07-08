package effective.chapter6.item37;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlantTest {

    @Test
    @DisplayName("EnumMap을 사용해 데이터와 열거 타입을 매핑한다")
    void test1() {
        // given
        List<Plant> garden =
                List.of(new Plant("hello1", Plant.LifeCycle.ANNUAL),
                        new Plant("hello2", Plant.LifeCycle.BIENNIAL),
                        new Plant("hello3", Plant.LifeCycle.BIENNIAL));

        Map<Plant.LifeCycle, Set<Plant>> plantsByLifCycle = new EnumMap<>(Plant.LifeCycle.class);

        // when
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifCycle.put(lc, new HashSet<>());
        }

        for (Plant p : garden) {
            plantsByLifCycle.get(p.lifeCycle).add(p);
        }

        // then
        Set<Plant> plants1 = plantsByLifCycle.get(Plant.LifeCycle.ANNUAL);
        Set<Plant> plants2 = plantsByLifCycle.get(Plant.LifeCycle.BIENNIAL);
        Set<Plant> plants3 = plantsByLifCycle.get(Plant.LifeCycle.PERENNIAL);
        assertThat(plants1.size()).isEqualTo(1);
        assertThat(plants2.size()).isEqualTo(2);
        assertThat(plants3.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("스트림을 사용한 코드 1 - EnumMap을 사용하지 않는다")
    void test2() {
        // given
        List<Plant> garden =
                List.of(new Plant("hello1", Plant.LifeCycle.ANNUAL),
                        new Plant("hello2", Plant.LifeCycle.BIENNIAL),
                        new Plant("hello3", Plant.LifeCycle.BIENNIAL));

        Plant[] gardenArray = garden.toArray(new Plant[0]);

        // then
        Map<Plant.LifeCycle, List<Plant>> plantsByLifCycle =
                Arrays.stream(gardenArray).collect(groupingBy(p -> p.lifeCycle));
        List<Plant> plants1 = plantsByLifCycle.get(Plant.LifeCycle.ANNUAL);
        List<Plant> plants2 = plantsByLifCycle.get(Plant.LifeCycle.BIENNIAL);
        List<Plant> plants3 = plantsByLifCycle.get(Plant.LifeCycle.PERENNIAL);

        assertThat(plants1.size()).isEqualTo(1);
        assertThat(plants2.size()).isEqualTo(2);
        assertThatThrownBy(() -> plants3.size())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("스트림을 사용한 코드 2 - EnumMap을 이용해 데이터와 열거 타입을 매핑한다")
    void test3() {
        // given
        List<Plant> garden =
                List.of(new Plant("hello1", Plant.LifeCycle.ANNUAL),
                        new Plant("hello2", Plant.LifeCycle.BIENNIAL),
                        new Plant("hello3", Plant.LifeCycle.BIENNIAL));

        Plant[] gardenArray = garden.toArray(new Plant[0]);

        // then
        EnumMap<Plant.LifeCycle, Set<Plant>> plantsByLifCycle =
                Arrays.stream(gardenArray).collect(groupingBy(p -> p.lifeCycle,
                () -> new EnumMap<>(Plant.LifeCycle.class), toSet()));

        Set<Plant> plants1 = plantsByLifCycle.get(Plant.LifeCycle.ANNUAL);
        Set<Plant> plants2 = plantsByLifCycle.get(Plant.LifeCycle.BIENNIAL);
        Set<Plant> plants3 =  plantsByLifCycle.get(Plant.LifeCycle.PERENNIAL);

        assertThat(plants1.size()).isEqualTo(1);
        assertThat(plants2.size()).isEqualTo(2);
        assertThatThrownBy(() -> plants3.size())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("중첩 EnumMap으로 데이터와 열거 타입 쌍을 연결한다")
    void test4() {
        // given
        Phase.Transition transition = Phase.Transition.from(Phase.SOLID, Phase.LIQUID);

        // expected
        assertThat(transition).isEqualTo(Phase.Transition.MELT);
    }
}