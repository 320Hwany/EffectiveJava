package effective.chapter2.item5.side;

public class Coke implements SideMenu {
    @Override
    public String getName() {
        return "coke";
    }

    @Override
    public int getPrice() {
        return 1500;
    }
}
