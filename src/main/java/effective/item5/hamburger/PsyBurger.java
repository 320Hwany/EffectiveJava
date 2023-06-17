package effective.item5.hamburger;

public class PsyBurger implements Hamburger {
    @Override
    public String getName() {
        return "PsyBurger";
    }

    @Override
    public int getPrice() {
        return 6000;
    }
}
