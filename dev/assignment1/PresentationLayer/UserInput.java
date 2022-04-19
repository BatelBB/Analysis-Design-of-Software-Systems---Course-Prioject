package assignment1.PresentationLayer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    private Scanner scanner = new Scanner(System.in);

    private static UserInput instance = null;
    UserInput(){

    }

    public static UserInput getInstance(){
        if(instance == null)
            instance = new UserInput();
        return instance;
    }

    int nextInt(String message){
        //goes in a loop to get int and prints the message we provided each time
        boolean retry = true;
        int nextInt = 0;
        while (retry) {
            try {
                UserOutput.getInstance().println(message);
                nextInt = Integer.parseInt(scanner.nextLine());
                retry = false;
            } catch (Exception e) {
                UserOutput.getInstance().println("Please try again.");
            }
        }
        return nextInt;
    }
}
