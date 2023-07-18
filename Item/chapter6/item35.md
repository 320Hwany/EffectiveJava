# 아이템 35 - ordinal 메소드 대신 인스턴스 필드를 사용하라

모든 열거 타입은 해당 상수가 그 열거 타입에서 몇 번째 위치인지를 반환하는 ordinal이라는 메소드를  
제공합니다. 열거 타입 상수와 연결된 정수값이 필요하면 ordinal 메소드를 이용하고 싶은 유혹에 빠집니다.   
다음 코드는 연주자가 1명인 솔로부터 10명인 디텍트까지 정의한 열거 타입입니다.     

## EnsembleBad
````java
public enum EnsembleBad {
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}
````

위 코드는 동작은 하지만 유지보수가 끔찍한 코드입니다.    
상수 선언 순서를 바꾸는 순간 numberOfMusicians가 오작동하며 이미 사용 중인 정수와 값이  
같은 상수는 추가할 방법이 없습니다. 또한 값을 중간에 비워둘 수도 없습니다.  

해결책은 간단합니다. 열거 타입 상수에 연결된 값을 ordinal 메소드로 얻지 말고 인스턴스 필드에 저장하면 됩니다.   

## EnsembleGood

````java
public enum EnsembleGood {

    SOLO(1), DUET(2), TRIO(3),
    QUARTET(4), QUINTET(5), SEXTET(6),
    SEPTET(7), OCTET(8), NONET(9),
    DECTET(10);

    private final int numberOfMusicians;

    EnsembleGood(int numberOfMusicians) {
        this.numberOfMusicians = numberOfMusicians;
    }

    public int numberOfMusicians() {
        return numberOfMusicians;
    }
}
````

ordinal 메소드는 처음부터 EnumSet, EnumMap 같이 열거 타입 기반의 범용 자료구조에 쓸 목적으로  
설계되었기 때문에 이런 용도가 아니라면 절대 사용하면 안됩니다.   

## 정리

ordinal 메소드는 몇번 째 위치인지 반환하여 편리한 것 같지만 유지보수가 힘들기 때문에    
인스턴스 필드를 사용하는 것이 훨씬 더 좋은 방법입니다.      
 
[아이템 35 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item35)                                                     
[아이템 35 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item35)     

