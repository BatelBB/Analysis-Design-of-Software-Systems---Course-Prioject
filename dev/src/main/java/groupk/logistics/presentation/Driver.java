package groupk.logistics.presentation;

public class Driver extends User {

    public Driver() {

    }

    public void act() {
        boolean logedOut = false;
        while (!logedOut)
        {
            System.out.println();
            System.out.println("Enter 1 to add new License");
            System.out.println("Enter 2 to print your future truckings");
            System.out.println("Enter 3 to print your trucking history");
            System.out.println("Enter 4 to print your whole truckings");
            System.out.println("Enter 5 to set weight for a truck");
            System.out.println("Enter 6 to update password");
            System.out.println("Enter 7 to logout");
            int choice = Main.getNumber();
            DriverFunctionality driverFunctionality = new DriverFunctionality();
            if(choice == 1)
                driverFunctionality.AddLicense();
            else if(choice == 2)
                driverFunctionality.printFutureTruckings();
            else if(choice == 3)
                driverFunctionality.printTruckingsHistory();
            else if(choice == 4)
                driverFunctionality.printTruckings();
            else if(choice == 5)
                driverFunctionality.setWeightForTrucking();
            else if(choice == 6)
                driverFunctionality.updatePassword();
            else if(choice == 7) {
                driverFunctionality.logOut();
                logedOut = true;
            }
            else
                System.out.println("You needed to select from the list...");
        }
    }
}
