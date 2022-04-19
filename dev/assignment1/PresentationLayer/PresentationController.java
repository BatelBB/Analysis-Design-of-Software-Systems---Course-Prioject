package assignment1.PresentationLayer;

import java.util.Scanner;
import java.util.Stack;

public class PresentationController {
    //** THIS IS A DEMO JUST TO SEE THE PROCESS **//
    private Scanner sc = new Scanner(System.in);
    public static void main(String args){

        Stack<Menu> navigation = new Stack<>();
        Menu mainMenu = createMainMenu();
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

        System.out.println("HI! Welcome to the supplier module, What would you like to do?");
        System.out.println("1. Create new supplier card\n" +
                "2. Create new order\n" +
                "3. ");
    }

    private static Menu createMainMenu() {
    }
}
