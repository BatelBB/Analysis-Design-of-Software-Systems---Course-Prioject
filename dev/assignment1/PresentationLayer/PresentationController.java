package assignment1.PresentationLayer;

import java.util.Scanner;
import java.util.Stack;

public class PresentationController {
    private Scanner sc = new Scanner(System.in);
    static UserInput input = UserInput.getInstance();
    static UserOutput output = UserOutput.getInstance();

    static Menu mainMenu = createMainMenu("Main");

    public static void main(String[] args){
        output.println("HI! Welcome to the supplier module, What would you like to do?");
        Stack<Menu> navigation = new Stack<>();
        createSubMenus();
        navigation.push(mainMenu);
        while(!navigation.isEmpty()) {
            int nextMenuResult = navigation.peek().run();
            if(nextMenuResult == Menu.REMAIN_ON_SAME_MENU) {
                // do nothing
            } else if (nextMenuResult == Menu.GO_TO_PARENT_MENU) {
                navigation.pop();
            } else {
                navigation.push(navigation.peek().getSubMenu(nextMenuResult));
            }
        }

    }

    private static void createSubMenus() {
        mainMenu.addSubmenus(new SupplierSubmenu("SupplierSubmenu"));
        mainMenu.addSubmenus(new ItemSubmenu("ItemSubmenu"));
        mainMenu.addSubmenus(new ItemSubmenu("OrderSubmenu"));
    }

    private static Menu createMainMenu(String name) {
        return new Menu(name);
    }
}
