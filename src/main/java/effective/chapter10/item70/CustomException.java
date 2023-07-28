package effective.chapter10.item70;

import java.io.IOException;

public class CustomException {

    // 검사 예외는 catch 하거나 throws 해야합니다.
    public void makeCheckedException() throws IOException {
        throw new IOException();
    }

    // 비검사 예외는 catch 하거나 throws를 강제하지 않습니다
    public void makeUncheckedException() {
        throw new RuntimeException();
    }
}
