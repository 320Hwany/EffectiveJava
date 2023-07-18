package effective.chapter8.item50;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

class PeriodTest {

    @Test
    @DisplayName("기간을 표현하는 클래스 - 불변식을 지키지 못했다")
    void test1() {
        // given
        Date start = new Date();
        Date end = new Date();
        PeriodBad p = new PeriodBad(start, end);

        // when
        Date end1 = new Date(p.end().getTime());
        end.setYear(78);
        Date end2 = new Date(p.end().getTime());

        // then
        assertThat(end1).isNotEqualTo(end2);
    }

    @Test
    @DisplayName("접근자 메소드가 내부의 가변 정보를 직접 드러냄 - PeriodBetter")
    void test2() {
        // given
        Date start = new Date();
        Date end = new Date();
        PeriodBetter p = new PeriodBetter(start, end);

        // when
        Date end1 = new Date(p.end().getTime());
        p.end().setYear(78);
        Date end2 = new Date(p.end().getTime());

        // then
        assertThat(end1).isNotEqualTo(end2);
    }

    @Test
    @DisplayName("접근자 메소드가 내부의 가변 정보를 직접 드러냄 - PeriodGood")
    void test3() {
        // given
        Date start = new Date();
        Date end = new Date();
        PeriodGood p = new PeriodGood(start, end);

        // when
        Date end1 = new Date(p.end().getTime());
        end.setYear(78);
        Date end2 = new Date(p.end().getTime());

        // then
        assertThat(end1).isEqualTo(end2);
    }

    @Test
    @DisplayName("접근자 메소드가 내부의 가변 정보를 직접 드러냄 - PeriodGood")
    void test4() {
        // given
        Date start = new Date();
        Date end = new Date();
        PeriodGood p = new PeriodGood(start, end);

        // when
        Date end1 = new Date(p.end().getTime());
        p.end().setYear(78);
        Date end2 = new Date(p.end().getTime());

        // then
        assertThat(end1).isEqualTo(end2);
    }
}