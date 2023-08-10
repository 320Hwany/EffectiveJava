package effective.chapter12.item85;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonTest {

    @Test
    @DisplayName("자바 직렬화는 부작용이 많으니 Json 방식으로 저장하는 것을 고려해야 합니다")
    void test1() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        Member member = new Member("이름", 20);

        // when
        String memberJson = objectMapper.writeValueAsString(member);
        Member readValue = objectMapper.readValue(memberJson, Member.class);

        // then
        assertThat(member).isEqualTo(readValue);
    }
}
