package effective.chapter4.item21;

public interface Singer {

    String sing();

    String dance();

    // 추가한 default 메소드는 구현했던 클래스 전부에게 영향을 미친다
    default String hello() {
        return "hello";
    }
}
