package effective.item5;

import effective.item5.hamburger.Hamburger;
import effective.item5.side.SideMenu;

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
