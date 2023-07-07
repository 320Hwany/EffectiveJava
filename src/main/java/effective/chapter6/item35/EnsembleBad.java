package effective.chapter6.item35;

// ordinal을 잘못 사용한 예 - 따라 하지 말것!
// 동작은 하지만 유지보수가 끔찍하다
public enum EnsembleBad {
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
