package effective.chapter8.item51;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MethodTest {

    @Test
    @DisplayName("매개변수 목록이 긴 경우 - 가능하면 줄여야 한다")
    void test1() {
        // given
        CustomList customList =
                new CustomList(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        // when
        int index = customList.findIndexBad(0, 5, "b");

        // then
        assertThat(index).isEqualTo(1);
    }

    @Test
    @DisplayName("매개변수 목록이 긴 메소드를 2개의 메소드로 분리했다")
    void test2() {
        // given
        CustomList customList =
                new CustomList(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        // when
        List<String> subList = customList.getSubList(0, 5);
        int index = customList.findIndexBetter(subList, "b");

        // then
        assertThat(index).isEqualTo(1);
    }

    @Test
    @DisplayName("매개변수 목록이 긴 update 메소드")
    void test3() {
        // given
        Member member = new Member("이름", 20, 170, 60);

        // when
        member.update1("update 이름", 30, 180, 70);

        // then
        assertThat(member.getName()).isEqualTo("update 이름");
        assertThat(member.getAge()).isEqualTo(30);
        assertThat(member.getHeight()).isEqualTo(180);
        assertThat(member.getWeight()).isEqualTo(70);
    }

    @Test
    @DisplayName("매개변수 여러 개를 묶어주는 도우미 클래스를 활용")
    void test4() {
        // given
        Member member = new Member("이름", 20, 170, 60);
        UpdateDto updateDto = new UpdateDto("update 이름", 30, 180, 70);

        // when
        member.update2(updateDto);

        // then
        assertThat(member.getName()).isEqualTo("update 이름");
        assertThat(member.getAge()).isEqualTo(30);
        assertThat(member.getHeight()).isEqualTo(180);
        assertThat(member.getWeight()).isEqualTo(70);
    }
}
