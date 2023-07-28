package effective.chapter10.item70;

import java.io.IOException;

public class CustomException {

    public void tryTodo1() {
        try {
            makeCheckedException();
        } catch (IOException e) {
            System.out.println("검사 예외 처리");
        }
    }

    public void tryTodo2() {
        makeUncheckedException();
    }

    // 검사 예외는 catch 하거나 throws 해야합니다.
    public void makeCheckedException() throws IOException {
        throw new IOException();
    }

    // 비검사 예외는 catch 하거나 throws를 강제하지 않습니다
    public void makeUncheckedException() {
        throw new RuntimeException();
    }
}
