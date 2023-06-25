package effective.chapter2.item6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StringTest {

    @Test
    @DisplayName("String은 리터럴 방식으로 선언해야 객체를 재사용할 수 있습니다")
    void test1() {
        // bad
        String bad1 = new String("hello");
        String bad2 = new String("hello");

        // good
        String good1 = "hello";
        String good2 = "hello";

        // expected
        assertThat(bad1).isNotSameAs(bad2);
        assertThat(good1).isSameAs(good2);
    }
}
