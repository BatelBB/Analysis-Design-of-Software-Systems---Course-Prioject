package assignment1.PresentationLayer;

import java.util.*;

public class Menu {
    public final String name;
    protected final UserInput input;
    protected final UserOutput output;
    private final List<Menu> submenus = new ArrayList<>();

    public Menu(String name, UserInput input, UserOutput output) {
        this.name = name;
        this.input = input;
        this.output = output;
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
        // can be overriden.
        throw new RuntimeException("not implemented yet");
    }

    public Menu getSubMenu(int menuIndex) {
        return submenus.get(menuIndex);
    }
}
