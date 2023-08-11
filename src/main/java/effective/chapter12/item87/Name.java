package effective.chapter12.item87;

import java.io.Serializable;

// 기본 직렬화 형태에 적합한 후보
public class Name implements Serializable {

    private final String lastName;

    private final String firstName;

    private final String middelName;

    public Name(final String lastName, final String firstName, final String middelName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middelName = middelName;
    }
}
