package effective.chapter12.item86;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.*;


class MemberTest {

    @Test
    @DisplayName("Serializable을 구현한 Member를 직렬화 후 역직렬화하여 기존 객체와 값이 같은지 확인합니다")
    void test1() {
        // given
        Member member = new Member("name", 20);

        // when
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
            out.writeObject(member);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // then
        Member deserializedMember = deserialize(byteArrayOutputStream);
        assertThat(deserializedMember).isEqualTo(member);
    }

    private static Member deserialize(final ByteArrayOutputStream byteArrayOutputStream) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
             ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
            return (Member) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}