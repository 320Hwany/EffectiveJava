package effective.chapter11.item83;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class InstanceFieldTest {

    @Test
    @DisplayName("지연 초기화를 포함한 필드를 초기화 하는 5가지 방식")
    void test1() {
        // given
        LazyInitializationClass lazyInitializationClass = new LazyInitializationClass();

        // 인스턴스 필드를 초기화하는 일반적인 방법을 사용함
        Integer field1 = lazyInitializationClass.getField1();

        // 인스턴스 필드의 지연 초기화 - synchronized 접근자 방식을 사용함
        Integer field2 = lazyInitializationClass.getField2();

        // 정적 필드용 지연 초기화 홀더 클래스 관용구을 사용함
        Integer field3 = LazyInitializationClass.getField3();

        // 인스턴스 필드 지연 초기화용 이중검사 관용구을 사용함
        Integer field4 = lazyInitializationClass.getField4();

        // 단일 검사 관용구 - 초기화가 중복해서 일어날 수 있다
        Integer field5 = lazyInitializationClass.getField5();

        // then
        assertThat(field1).isEqualTo(10);
        assertThat(field2).isEqualTo(10);
        assertThat(field3).isEqualTo(10);
        assertThat(field4).isEqualTo(10);
        assertThat(field5).isEqualTo(10);
    }
}