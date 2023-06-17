package effective.item5.side;

public class FrenchFries implements SideMenu {
    @Override
    public String getName() {
        return "FrenchFries";
    }

    @Override
    public int getPrice() {
        return 2000;
    }
}
