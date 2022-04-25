package PresentaionLayer;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Super Lee transportaion Layer!");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        boolean wantToLeaveTheSystem = false;
        while (!wantToLeaveTheSystem) {
            System.out.println("Enter 1 to Register");
            System.out.println("Enter 2 to Login");
            System.out.println("Enter 3 to Close the system");
            String answer = myObj.nextLine();
            if (answer.equals("1")) {
                RegisterWindow registerWindow = new RegisterWindow();
                registerWindow.register();
            }
            else if (answer.equals("2")) {
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.login();
            }
            else if (answer.equals("3"))
                wantToLeaveTheSystem = true;
            else
                System.out.println("wrong input!");
        }
    }

    public static int getNumber() {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        int numToReturn = 0;
        while (!done) {
            String input = in.next();
            if (input == null) {
                System.out.println("Please enter your selection");
            }
            try {
                numToReturn = Integer.parseInt(input);
                done = true;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter number");
            }
        }
        return numToReturn;
    }

    public static String getChoiceFromArray(String[] choicesArray) {
        int choice = getNumber();
        while (choice > choicesArray.length) {
            System.out.println("Please enter number from the options list");
            choice = getNumber();
        }
        return choicesArray[choice - 1];
    }

    public static int printOptionsList(List<String> optionsList) {
        int counter = 1;
        for (String option : optionsList) {
            System.out.println(counter + ". " + option);
            counter++;
        }
        int choice = Main.getNumber();
        while (choice < 1 | choice > optionsList.size()) {
            System.out.println("Enter number from the list");
            choice = Main.getNumber();
        }
        return choice;
    }

}
