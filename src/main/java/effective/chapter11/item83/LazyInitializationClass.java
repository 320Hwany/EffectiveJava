package effective.chapter11.item83;

public class LazyInitializationClass {

    // 인스턴스 필드를 초기화하는 일반적인 방법
    private final Integer field1 = 10;


    public Integer getField1() {
        return field1;
    }

    // 인스턴스 필드의 지연 초기화 - synchronized 접근자 방식
    private Integer field2;

    public synchronized Integer getField2() {
        if (field2 == null) {
            field2 = 10;
        }
        return field2;
    }


    // 정적 필드용 지연 초기화 홀더 클래스 관용구
    static class FieldHolder {
        static final Integer field3 = 10;
    }

    public static Integer getField3() {
        return FieldHolder.field3;
    }


    // 인스턴스 필드 지연 초기화용 이중검사 관용구
    private volatile Integer field4;

    public Integer getField4() {
        Integer result = field4;
        if (result != null) {
            return result;  // 첫 번째 검사 (락 사용 안함)
        }

        synchronized (this) {
            if (field4 == null) {
                field4 = 10; // 두 번째 검사 (락 사용)
            }
            return field4;
        }
    }
    
    // 단일 검사 관용구 - 초기화가 중복해서 일어날 수 있다
    private volatile Integer field5;

    public Integer getField5() {
        Integer result = field5;
        if (result == null) {
            return field5 = result = 10;  
        }
        return result;
    }
}
