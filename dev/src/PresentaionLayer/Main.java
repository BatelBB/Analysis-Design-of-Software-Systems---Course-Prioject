package PresentaionLayer;

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
            } else if (answer.equals("2")) {
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.login();
            } else if (answer.equals("3")) wantToLeaveTheSystem = true;
            else {
                System.out.println("wrong input!");
            }
        }

    }

}
