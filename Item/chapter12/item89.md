# 아이템 89 - 인스턴스 수를 통제해야 한다면 readResovle 보다는 열거 타입을 사용하라

### 잘못된 싱글톤 - transient가 아닌 참조 필드를 가지고 있다

Elvis 클래스가 Serializable를 구현한다면 readResolve 메소드를 추가해 싱글톤이라는 속성을 유지할 수 있습니다.    
이때 readResovle를 인스턴스 통제 목적으로 사용한다면 객체 참조 타입 인스턴스 필드 모두 transient로     
선언해야 합니다.

````java
public class Elvis implements Serializable {

    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    private String[] favoriteSongs = {"Hound Dogs", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
````

### 도둑 클래스

````java
public class ElvisStealer implements Serializable {

    static Elvis impersonator;

    private Elvis payLoad;

    private Object readResolve() {
        // resolve 되기 전의 Elvis 인스턴스의 참조를 저장한다
        impersonator = payLoad;

        // favoriteSongs 필드에 맞는 타입의 객체를 반환한다
        return new String[]{"A Fool Such as I"};
    }

    private static final long serialVersionUID = 0;
}
````

필드를 transient로 선언하지 않으면 위와 같은 도둑 클래스로 직렬화의 허점을 이용할 수 있습니다.    
favoriteSongs 필드를 transient로 선언하여 이 문제를 고칠 수 있지만 Elvis를 원소 하나짜리    
열거 타입으로 바꾸는 편이 더 나은 선택입니다.    

### 열거 타입 싱글톤 - 전통적인 싱글톤보다 우수하다

````java
public enum ElvisBetter {

    INSTANCE;

    private String[] favoriteSongs = {"Hound Dogs", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
````

## 정리

불변식을 지키기 위해 인스턴스를 통제해야 한다면 가능한 한 열거 타입을 사용해야 합니다.    
여의치 않은 상황에서 직렬화와 인스턴스 통제가 모두 필요하다면 readResolve 메소드를 작성해 넣어야 하고    
그 클래스에서 모든 참조 타입 인스턴스 플디를 transient로 선언해야 합니다.      

[아이템 89 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter12/item89)       