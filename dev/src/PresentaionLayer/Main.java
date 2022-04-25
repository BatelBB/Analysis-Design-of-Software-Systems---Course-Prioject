package PresentaionLayer;

import java.time.LocalDateTime;
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

    public static LocalDateTime getDateFromUser() {
        boolean found = false;
        LocalDateTime toReturn = null;
        while (!found) {
            System.out.println("Enter year");
            int year = getNumber();
            while (year < 0 | year > 2100) {
                System.out.println("please enter a normal year");
                year = getNumber();
            }
            System.out.println("Enter month");
            int month = getNumber();
            while (month < 1 | year > 12) {
                System.out.println("don't be stupid. month need to be between 1-12");
                month = getNumber();
            }
            System.out.println("Enter day");
            int day = getNumber();
            while (day < 1 | day > 31) {
                System.out.println("You do not know how many days there are in a month?");
                day = getNumber();
            }
            int hour = getNumber();
            while (hour < 0 | hour > 23) {
                System.out.println("You do not know how many hours there are in a day?");
                hour = getNumber();
            }
            int minute = getNumber();
            while (minute < 0 | minute > 59) {
                System.out.println("You do not know how many minutes there are in a hour?");
                minute = getNumber();
            }
            try {
                toReturn = LocalDateTime.of(year, month, day, hour, minute);
                found = true;
            }
            catch (Exception e) {
                System.out.println("Oops, something got wrong. Maybe the date is not valid.");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String choice = getChoiceFromArray(new String[]{"Yes", "No"});
                if (choice.equals("No"))
                    found = true;
            }
        }
        return toReturn;
    }

}
