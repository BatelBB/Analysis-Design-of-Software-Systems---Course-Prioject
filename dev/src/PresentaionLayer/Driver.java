package PresentaionLayer;

import java.util.Scanner;

public class Driver extends User {
    public Driver(String name, String username) {
        super(name, username);
    }

    public void act() throws Exception {
        boolean logedOut = false;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        while (!logedOut)
        {
            System.out.println("Enter 1 to add new License");
            System.out.println("Enter 2 to print your future truckings");
            System.out.println("Enter 3 to print your trucking history");
            System.out.println("Enter 4 to print your whole truckings");
            System.out.println("Enter 5 to set weight for a truck");
            System.out.println("Enter 6 to update password");
            System.out.println("Enter 7 to logout");
            String choice = myObj.nextLine();
            if(!choice.equals("1") | !choice.equals("2")|!choice.equals("3") | !choice.equals("4")|!choice.equals("5") | !choice.equals("6")|!choice.equals("7"))
                System.out.println("wrong input!");

            else
            {
                DriverFuctionality driverFuctionality = null;
                if(choice.equals("1"))
                {
                    System.out.println("Enter type of license");
                    String license = myObj.nextLine();
                    driverFuctionality = new AddLicense(license);
                }
                if(choice.equals("2")) driverFuctionality = new PrintFuture();
                if(choice.equals("3")) driverFuctionality = new PrintHistory();
                if(choice.equals("4")) driverFuctionality = new PrintTruckings();
                if(choice.equals("5"))
                {

                }
                if(choice.equals("6"))
                {

                }
                if(choice.equals("7"))
                {
                    UserFunctionality userFunctionality = new Logout();
                    userFunctionality.act();
                    logedOut = true;
                }

                if(!choice.equals("7")) driverFuctionality.act();
            }


        }
    }
}
