package assignment1.PresentationLayer;

import java.util.*;


public class Menu {
    public final String name;
    protected final UserInput input = UserInput.getInstance();
    protected final UserOutput output = UserOutput.getInstance();
    private final List<Menu> submenus = new ArrayList<>();

    public Menu(String name) {
        this.name = name;

    }

    public static final int
            REMAIN_ON_SAME_MENU = -1, GO_TO_PARENT_MENU = -2;
    /*
    returns index of submenu to go to,
    OR REMAIN_ON_SAME_MENU
    OR GO_TO_PARENT_MENU
     */
    public int run() {
        // default is to show submenus and allow to choose & go back.
        // can be overridden.
        return input.nextInt(
                "0. Supplier Menu\n" +
                "1. Order Menu\n" +
                "2. Item Menu");
    }

    public Menu getSubMenu(int menuIndex) {
        return submenus.get(menuIndex);
    }

    public void addSubmenus(Menu element){
        submenus.add(element);
    }
}
