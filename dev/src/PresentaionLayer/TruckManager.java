package PresentaionLayer;

import java.util.Scanner;

public class TruckManager extends User{
    public TruckManager(String name, String username) {
        super(name, username);
    }

    public void act() throws Exception {
        boolean logedOut = false;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (!logedOut)
        {
            System.out.println("Enter 1 to remove Trucking");
            System.out.println("Enter 2 to print your board");
            System.out.println("Enter 3 to print truckings history");
            System.out.println("Enter 4 to print your drivers usernames");
            System.out.println("Enter 5 to print the vehicles registration plates");
            System.out.println("Enter 6 to add trucking");
            System.out.println("Enter 7 to print a board of one of your drivers");
            System.out.println("Enter 8 to add a vheicle");
            System.out.println("Enter 9 to print truckings history of a driver");
            System.out.println("Enter 10 to print future trucking of a driver");
            System.out.println("Enter 11 to print a board of one of your vheicle");
            System.out.println("Enter 12 to logout");
            String choice = myObj.nextLine();
            if(!choice.equals("1") | !choice.equals("2")|!choice.equals("3") | !choice.equals("4")|!choice.equals("5") | !choice.equals("6")|!choice.equals("7")
            |!choice.equals("8") | !choice.equals("9")|!choice.equals("10") | !choice.equals("11")|!choice.equals("12"))
                System.out.println("wrong input!");

            else
            {
                if(choice.equals("12"))
                {
                    UserFunctionality userFunctionality = new Logout();
                    userFunctionality.act();
                    logedOut = true;
                }
                else System.out.println("ToDo");

            }


        }
    }
}
