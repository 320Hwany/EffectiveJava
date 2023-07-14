package effective.chapter7.item46;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.*;

public class StreamTest {

    @Test
    @DisplayName("빈도표에서 가장 흔한 단어 10개를 뽑아내는 파이프라인")
    void test1() {
        // given
        Map<String, Integer> freq = new HashMap<>();

        // 단어와 빈도수 추가
        freq.put("apple", 5);
        freq.put("banana", 3);
        freq.put("orange", 7);
        freq.put("grape", 2);
        freq.put("kiwi", 4);
        freq.put("pear", 6);
        freq.put("pineapple", 8);
        freq.put("mango", 9);
        freq.put("strawberry", 10);
        freq.put("cherry", 1);

        // when
        List<String> topTen = freq.keySet().stream()
                .sorted(comparing(freq::get).reversed())
                .limit(10)
                .toList();

        // then
        assertThat(topTen.size()).isEqualTo(10);
        assertThat(topTen.get(0)).isEqualTo("strawberry");
        assertThat(topTen.get(9)).isEqualTo("cherry");
    }

    @Test
    @DisplayName("toMap 수집기를 사용하여 문자열을 열거 타입 상수에 매핑한다")
    void test2() {
        // given && when
        Map<String, Operation> stringToEnum = Stream.of(Operation.values())
                .collect(toMap(Objects::toString, e -> e));

        // then
        assertThat(stringToEnum.get("+")).isEqualTo(Operation.PLUS);
        assertThat(stringToEnum.get("-")).isEqualTo(Operation.MINUS);
        assertThat(stringToEnum.get("*")).isEqualTo(Operation.TIMES);
        assertThat(stringToEnum.get("/")).isEqualTo(Operation.DIVIDE);
    }

    @Test
    @DisplayName("각 키와 해당 키의 특정 원소를 연관 짓는 맵을 생성하는 수집기")
    void test3() {
        // given
        Artist artist1 = new Artist("artist1");
        Artist artist2 = new Artist("artist2");

        Albums album1 = new Albums(artist1, "album1", 10000);
        Albums album2 = new Albums(artist1, "album2", 20000);
        Albums album3 = new Albums(artist1, "album3", 30000);
        Albums album4 = new Albums(artist2, "album4", 40000);
        Albums album5 = new Albums(artist2, "album5", 50000);

        List<Albums> albums = List.of(album1, album2, album3, album4, album5);

        // when
        Map<Artist, Albums> topHits = albums.stream()
                .collect(toMap(Albums::getArtist, a -> a, maxBy(comparing(Albums::getSales))));

        // then
        Albums artist1Album = topHits.get(artist1);
        Albums artist2Album = topHits.get(artist2);
        assertThat(artist1Album.getAlbumName()).isEqualTo("album3");
        assertThat(artist2Album.getAlbumName()).isEqualTo("album5");
    }

    @Test
    @DisplayName("마지막에 쓴 값을 취하는 수집기")
    void test4() {
        // given
        Artist artist1 = new Artist("artist1");
        Artist artist2 = new Artist("artist2");

        Albums album1 = new Albums(artist1, "album1", 10000);
        Albums album2 = new Albums(artist1, "album2", 20000);
        Albums album3 = new Albums(artist1, "album3", 30000);
        Albums album4 = new Albums(artist2, "album4", 40000);
        Albums album5 = new Albums(artist2, "album5", 50000);

        List<Albums> albums = List.of(album1, album2, album3, album4, album5);

        // when
        Map<Artist, String> collect = albums.stream()
                .collect(toMap(Albums::getArtist, Albums::getAlbumName, (oldVal, newVal) -> newVal));

        // then
        assertThat(collect.get(artist1)).isEqualTo("album3");
        assertThat(collect.get(artist2)).isEqualTo("album5");
    }

    @Test
    @DisplayName("groupingBy 분류 함수 하나를 인수로 받아 맵을 반환한다")
    void test5() {
        // given
        Artist artist1 = new Artist("artist1");
        Artist artist2 = new Artist("artist2");

        Albums album1 = new Albums(artist1, "album1", 10000);
        Albums album2 = new Albums(artist1, "album2", 20000);
        Albums album3 = new Albums(artist1, "album3", 30000);
        Albums album4 = new Albums(artist2, "album4", 40000);
        Albums album5 = new Albums(artist2, "album5", 50000);

        List<Albums> albums = List.of(album1, album2, album3, album4, album5);

        // when
        Map<Artist, List<Albums>> artistGroup = albums.stream()
                .collect(groupingBy(Albums::getArtist));

        // then
        List<Albums> artist1Group = artistGroup.get(artist1);
        List<Albums> artist2Group = artistGroup.get(artist2);

        assertThat(artistGroup.size()).isEqualTo(2);
        assertThat(artist1Group.size()).isEqualTo(3);
        assertThat(artist2Group.size()).isEqualTo(2);
    }
}
