package effective.chapter2.item5.hamburger;

public class BulgogiBurger implements Hamburger {
    @Override
    public String getName() {
        return "Bolgogi";
    }

    @Override
    public int getPrice() {
        return 5000;
    }
}
