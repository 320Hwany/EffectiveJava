package effective.chapter8.item51;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MethodTest {

    @Test
    @DisplayName("매개변수 목록이 긴 경우")
    void test1() {
        // given
        CustomList customList =
                new CustomList(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"), "e");

        // when
        int index =
                customList.findIndex1(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i", "j"));

        // then
        assertThat(index).isEqualTo(4);
    }

    @Test
    @DisplayName("매개변수 목록이 긴 메소드를 2개의 메소드로 쪼갠다")
    void test2() {
        // given
        CustomList customList =
                new CustomList(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"), "e");

        // when
        int index1 = customList.findIndex2(0, 2);
        int index2 = customList.findIndex2(3, 5);
        int index3 = customList.findIndex2(6, 9);

        // then
        assertThat(index1).isEqualTo(-1);
        assertThat(index2).isEqualTo(4);
        assertThat(index3).isEqualTo(-1);
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
