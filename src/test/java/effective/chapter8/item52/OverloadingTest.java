package effective.chapter8.item52;

import effective.chapter8.item52.overriding.Champagne;
import effective.chapter8.item52.overriding.SparklingWine;
import effective.chapter8.item52.overriding.Wine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static effective.chapter8.item52.overloading.CollectionClassifier.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OverloadingTest {

    @Test
    @DisplayName("오버로딩은 어느 메소드를 호출할지가 컴파일 타임에 정해짐")
    void test1() {
        // given
        Set<String> sets = new HashSet<>();
        List<BigInteger> bigIntegers = new ArrayList<>();
        Map<String, String> maps = new HashMap<>();

        Collection<?>[] collections = {sets, bigIntegers, maps.values()};

        // when
        for (Collection<?> c : collections) {
            assertThat(classify(c)).isEqualTo("그 외");
        }
    }

    @Test
    @DisplayName("컴파일타임 타입이 모두 Wine인 것과 무관하게 항상 가장 하위에서 정의한 재정의 메소드가 실행됨")
    void test2() {
        // given
        Wine wine = new Wine();
        SparklingWine sparklingWine = new SparklingWine();
        Champagne champagne = new Champagne();

        List<Wine> wines = List.of(wine, sparklingWine, champagne);

        // when
        Wine wine1 = wines.get(0);
        Wine wine2 = wines.get(1);
        Wine wine3 = wines.get(2);

        // then
        assertThat(wine1.name()).isEqualTo("포도주");
        assertThat(wine2.name()).isEqualTo("발포성 포도주");
        assertThat(wine3.name()).isEqualTo("샴페인");
    }

    @Test
    @DisplayName("List의 remove 메소드 오버로딩으로 인해 예상한대로 동작하지 않을 수 있다")
    void test3() {
        // given
        Set<Integer> set = new TreeSet<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        // when
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list1.add(i);
            list2.add(i);
        }

        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list1.remove(i);
            list2.remove((Integer) i);
        }

        // then
        assertThat(set.contains(-3)).isTrue();
        assertThat(set.contains(-2)).isTrue();
        assertThat(set.contains(-1)).isTrue();

        assertThat(list1.contains(-2)).isTrue();
        assertThat(list1.contains(0)).isTrue();
        assertThat(list1.contains(2)).isTrue();

        assertThat(list2.contains(-3)).isTrue();
        assertThat(list2.contains(-2)).isTrue();
        assertThat(list2.contains(-1)).isTrue();
    }
}
