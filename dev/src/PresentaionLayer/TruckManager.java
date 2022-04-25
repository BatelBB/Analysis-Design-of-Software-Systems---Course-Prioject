package PresentaionLayer;

import java.util.Scanner;

public class TruckManager extends User{
    public TruckManager(String name, String username) {
        super(name, username);
    }

    public void act() throws Exception {
        boolean logedOut = false;
        while (!logedOut)
        {
            System.out.println();
            System.out.println("Enter 1  to remove Trucking");
            System.out.println("Enter 2  to print your board");
            System.out.println("Enter 3  to print truckings history");
            System.out.println("Enter 4  to print future truckings");
            System.out.println("Enter 5  to print your drivers usernames");
            System.out.println("Enter 6  to print the vehicles registration plates");
            System.out.println("Enter 7  to add trucking");
            System.out.println("Enter 8  to print a board of one of your drivers");
            System.out.println("Enter 9  to print truckings history of a driver");
            System.out.println("Enter 10 to print future trucking of a driver");
            System.out.println("Enter 11 to add a vehicle");
            System.out.println("Enter 12 to print a board of one of your vheicle");
            System.out.println("Enter 13 to update password");
            System.out.println("Enter 14 to logout");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                return;
            else if(choice == 2)
                truckManagerFunctionality.printTruckingsBoard();
            else if(choice == 3)
                truckManagerFunctionality.printTruckingsHistory();
            else if(choice == 4)
                truckManagerFunctionality.printFutureTruckings();
            else if(choice == 5)
                return;
            else if(choice == 6)
                return;
            else if(choice == 7)
                return;
            else if(choice == 8)
                return;
            else if(choice == 9)
                return;
            else if(choice == 10)
                return;
            else if(choice == 11)
                return;
            else if(choice == 12)
                truckManagerFunctionality.updatePassword();
            else if(choice == 13) {
                truckManagerFunctionality.logOut();
                logedOut = true;
            }
            else
                System.out.println("You needed to select from the list...");
        }
    }
}
