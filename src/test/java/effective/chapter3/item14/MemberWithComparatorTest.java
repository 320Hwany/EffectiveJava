package effective.chapter3.item14;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberWithComparatorTest {

    @Test
    @DisplayName("Compartor를 이용해서 순서를 비교할 수 있습니다")
    void test() {
        // given
        MemberWithComparator member1 = new MemberWithComparator("회원 1",20, 172, 60);
        MemberWithComparator member2 = new MemberWithComparator("회원 2",21, 170, 70);

        // when
        List<MemberWithComparator> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        Collections.sort(members);

        // then
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(members.get(1)).isEqualTo(member2);
    }
}