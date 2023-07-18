# 아이템 5 - 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

클래스가 여러 자원 인스턴스를 지원하며 클라이언트가 원하는 자원을 사용하도록 하려면    
인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식을 사용하면 됩니다.    

## HamburgerSet

```java
public class HamburgerSet {

    private final Hamburger hamburger;
    private final SideMenu sideMenu;

    public HamburgerSet(Hamburger hamburger, SideMenu sideMenu) {
        this.hamburger = hamburger;
        this.sideMenu = sideMenu;
    }

    public String getSetName() {
        return hamburger.getName() + " " + sideMenu.getName();
    }

    public int getSetPrice() {
        return hamburger.getPrice() + sideMenu.getPrice();
    }
}
```

위와 같은 햄버거 세트가 있을 때 햄버거와 사이드 메뉴는 각각의 햄버거 세트마다 다를 수 있습니다.   
햄버거와 사이드 메뉴는 인터페이스로 구현을 하고 햄버거 세트를 생성할 때 클라이언트에서 햄버거와 사이드 메뉴 정보를   
주입해주는 방법을 사용하는 것을 의존 객체 주입이라고 합니다.   
이 방식은 불변을 보장하여 같은 자원을 사용하려는 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있게 합니다.   

## 정리

클래스가 내부적으로 하나 이상의 자원에 의존한다면 클래스가 직접 만들도록 하지 않고    
필요한 자원을 생성자(혹은 정적 팩토리나 빌더)에 넘겨주는 의존 객체 주입 방식을 사용하는 것이 좋습니다.    
이 방식은 클래스의 유연성, 재사용성, 테스트 용이성이라는 이점을 가져다 줍니다.    

[아이템 5 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter2/item5)            
[아이템 5 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter2/item5)      


