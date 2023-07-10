package effective.chapter6.item41;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberTest {

    @Test
    @DisplayName("마커 인터페이스(Serializable)을 활용해서 인스턴스들의 타입을 구분할 수 있습니다")
    void test1() throws IOException {
        File f= new File("test.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));

        objectOutputStream.writeObject(new Member1("name", 20));
    }

    @Test
    @DisplayName("마커 인터페이스를 사용하는 주된 이유는 컴파일타임 오류 검출인데 이점을 살리지 못한 케이스입니다")
    void test2() throws IOException {
        File f= new File("test.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));

        assertThatThrownBy(() -> objectOutputStream.writeObject(new Member2("name", 20)))
                .isInstanceOf(NotSerializableException.class);
    }
}
