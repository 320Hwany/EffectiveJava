package effective.chapter3.item14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class MemberTest {

    @Test
    @DisplayName("Comparable을 구현하여 Member 객체의 순서를 정렬할 수 있습니다")
    void test1() {
        // given
        Member member1 = new Member("회원 1",21, 172, 60);
        Member member2 = new Member("회원 2",21, 170, 70);

        // when
        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        Collections.sort(members);

        // then
        assertThat(members.get(0)).isEqualTo(member2);
        assertThat(members.get(1)).isEqualTo(member1);
    }

    @Test
    @DisplayName("Comparator를 구현하여 기존 정렬 순서를 바꿀 수 있습니다")
    void test2() {
        // given
        Member member1 = new Member("회원 1",22, 170, 60);
        Member member2 = new Member("회원 2",23, 172, 70);

        // when
        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        Collections.sort(members, Comparator.comparingInt(Member::getAge)
                .thenComparingDouble(Member::getHeight)
                .thenComparingDouble(Member::getWeight));

        // then
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(1)).isEqualTo(member2);
    }
}