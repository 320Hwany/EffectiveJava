# 아이템 36 - 비트 필드 대신 EnumSet을 사용하라

열거한 값들이 주로 집합으로 사용될 경우가 있습니다.      
각 상수에 서로 다른 2의 거듭제곱 값을 할당한 정수 열거 패턴을 예시로 들겠습니다.    

##  비트 필드 열거 상수 - bad
````
public class TextBad {

    public static final int STYLE_BOLD = 1 << 0;
    public static final int STYLE_ITALIC = 1 << 1;
    public static final int STYLE_UNDERLINE = 1 << 2;
    public static final int STYLE_STRIKETHROUGH = 1 << 3;

    public int applyStyles(int styles) {
        System.out.println(styles);
        return styles;
    }
}
````

````
TextBad textBad = new TextBad();
int styles = textBad.applyStyles(TextBad.STYLE_BOLD | TextBad.STYLE_ITALIC);
````

위와 같은 방식으로 비트 필드를 사용하면 비트별 연산을 사용해 합집합, 교집합과 같은 집합 연산을   
효율적으로 수행할 수 있습니다. 하지만 비트 필드는 정수 열거 상수의 단점을 그대로 지니며 해석하기가   
훨씬 어렵습니다. 또한 최대 몇 비트가 필요한지를 API 작성시 미리 예측하여 적절한 타입(int, long)을   
선택해야 합니다. API를 수정하지 않고는 비트 수를 더 늘릴 수 없기 때문입니다.    

java.util 패키지의 EnumSet 클래스는 열거 타입 상수의 값으로 구성된 집합을 효과적으로 표현해줍니다.   

## EnumSet - 비트 필드를 대체하는 현대적인 기법
````
public class TextGood {

    public enum Style {
        BOLD,
        ITALIC,
        UNDERLINE,
        STRIKETHROUGH;
    }

    // 어떤 Set을 넘겨도 되나, EnumSet이 가장 좋다
    public Set<Style> applyStyles(Set<Style> styles) {
        System.out.println(styles);
        return styles;
    }
}
````

````
TextGood textGood = new TextGood();
Set<Style> styles2 = textGood.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
````

## 정리

열거할 수 있는 타입을 한데 모아 집합 형태로 사용한다고 해도 비트 필드를 사용할 이유는 없습니다.   
EnumSet 클래스가 비트 필드 수준의 명료함과 성능을 제공하고 열거 타입의 장점을 주기 때문입니다.    
EnumSet의 유일한 단점은 불변 EnumSet을 만들 수 없다는 것인데 Collections.unmodifiableSet을 이용해   
EnumSet을 감싸 불변을 보장할 수 있습니다.     

[아이템 36 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter6/item36)                                                      
[아이템 36 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter6/item36)             