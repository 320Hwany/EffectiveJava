# 아이템 65 - 리플렉션보다는 인터페이스를 사용하라

리플렉션 기능을 이용하면 프로그램에서 임의의 클래스에 접근할 수 있습니다.    
Class 객체가 주어지면 그 클래스의 생성자, 메소드, 필드에 해당하는 인스턴스를 가져올 수 있고    
이어서 이 인스턴스들로는 그 클래스의 멤버 이름, 필드 타입, 메소드 시그니처 등을 가져올 수 있습니다.     

리플렉션을 이용하면 컴파일 당시에 존재하지 않던 클래스도 이용할 수 있는데 단점이 있습니다.    
1. 컴파일타임 타입 검사가 주는 이점을 하나도 누릴 수 없습니다.
2. 리플렉션을 이용하면 코드가 지저분해지고 장황해집니다.
3. 리플렉션을 통한 메소드 호출은 일반 메소드 호출보다 훨씬 느립니다.

코드 분석 도구나 의존관계 주입 프레임워크처럼 리플렉션을 써야 하는 복잡한 애플리케이션이 몇가지 있습니다.     
하지만 리플렉션의 단점이 명확하기 때문에 이런 도구들마저 리플렉션 사용을 점차 줄이고 있습니다.   
예를들어 스프링의 DI 방식 중에서 생성자 주입을 사용하면 다른 주입 방식보다 리플렉션을 덜 사용합니다.      

리플렉션은 아주 제한된 형태로만 사용해야 그 단점을 피하고 이점만 취할 수 있습니다.    
리플렉션은 인스턴스 생성에만 쓰고 이렇게 만든 인스턴스는 인터페이스나 상위 클래스로 참조해 사용해야 합니다.    

## ReflectionTest

````java
public class ReflectionTest {

    @Test
    @DisplayName("리플렉션으로 생성하고 인터페이스로 참조해 활용한다")
    void test1() {
        // given
        String[] args = new String[]{"java.util.HashSet", "hello", "world"};

        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("클래스를 찾을 수 없습니다.");
        }

        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("매개변수 없는 생성자를 찾을 수 없습니다.");
        }

        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fatalError("생성자에 접근할 수 없습니다");
        } catch (InstantiationException e) {
            fatalError("클래스를 인스턴스화할 수 없습니다.");
        } catch (InvocationTargetException e) {
            fatalError("생성자가 예외를 던졌습니다: " + e.getCause());
        } catch (ClassCastException e) {
            fatalError("Set을 구현하지 않은 클래스입니다.");
        }

        s.addAll(Arrays.asList(args).subList(1, args.length));
        assertThat(s.size()).isEqualTo(2);
        assertThat(s.contains("hello")).isTrue();
        assertThat(s.contains("world")).isTrue();
    }

    private static void fatalError(String msg) {
        System.out.println(msg);
        System.exit(1);
    }
}
````

위 예시는 리플렉션의 단점 2가지를 보여줍니다. 
1. 런타임에 총 여섯가지나 되는 예외를 던질 수 있습니다. 인스턴스를 리플렉션 없이 생성했다면
모두 컴파일타임에 잡아낼 수 있었을 예외들입니다.    
2. 클래스 이름만으로 인스턴스를 생성해내기 위해 무려 25줄이나 되는 코드를 작성했습니다.    
리플렉션이 아니라면 생성자 호출 한 줄로 끝났을 일입니다.    

2가지 단점 모두 객체를 생성하는 부분에만 국한되고 객체가 일단 만들어지면 그 후의 코드는 다른      
Set 인스턴스를 사용할 때와 똑같습니다.    

## 정리

리플렉션 복잡한 특수 시스템을 개발할 때 필요한 강력한 기능이지만 단점도 많습니다.    
컴파일 타임에는 알 수 없는 클래스를 사용하는 프로그램을 작성한다면 리플렉션을 사용해야 할 것입니다.     
단, 되도록 객체 생성에만 사용하고 생성한 객체를 이용할 때는 적절한 인터페이스나 컴파일타임에 알 수 있는    
상위 클래스로 형변환해 사용해야 합니다.         

[아이템 65 - 예제 코드](https://github.com/320Hwany/EffectiveJava/tree/main/src/main/java/effective/chapter9/item65)                                                                                                               
[아이템 65 - 학습 테스트](https://github.com/320Hwany/EffectiveJava/tree/main/src/test/java/effective/chapter9/item65)             
