package effective.chapter4.item20.member;

public class Member {

    private String hi = null;
    private String hello = null;
    private String howAreYou = null;


    public void hi() {
        hi = "Hi";
    }

    public HelloHowAreYou helloHowAreYou() {
        return new HelloHowAreYou() {

            @Override
            public void hello() {
                hello = "Hello";
            }

            @Override
            public void howAreYou() {
                howAreYou = "How are you";
            }
        };
    }

    public String getHi() {
        return hi;
    }

    public String getHello() {
        return hello;
    }

    public String getHowAreYou() {
        return howAreYou;
    }
}
