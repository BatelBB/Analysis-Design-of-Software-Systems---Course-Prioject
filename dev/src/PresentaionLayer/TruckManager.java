package PresentaionLayer;

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
            System.out.println("Enter 12  to get register code");
            System.out.println("Enter 13 to update password");
            System.out.println("Enter 14  to print a board of one of your vehicles");
            System.out.println("Enter 15  to print truckings history of a vehicle");
            System.out.println("Enter 16 to print future trucking of a vehicle");
            System.out.println("Enter 17 to add sources to a trucking");
            System.out.println("Enter 18  to update sources of a trucking");
            System.out.println("Enter 19  to add sources to a trucking");
            System.out.println("Enter 20 to update destinations of a trucking");
            System.out.println("Enter 21  to update driver on a trucking");
            System.out.println("Enter 22 to update vehicle on a trucking");
            System.out.println("Enter 23  to update date on a trucking");
            System.out.println("Enter 24  to remove a product from a trucking");
            System.out.println("Enter 25 to add a product to a trucking");
            System.out.println("Enter 26 to logout");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 0)
                truckManagerFunctionality.getRegisterCode();
            else if(choice == 1)
                truckManagerFunctionality.removeTrucking();
            else if(choice == 2)
                truckManagerFunctionality.printTruckingsBoard();
            else if(choice == 3)
                truckManagerFunctionality.printTruckingsHistory();
            else if(choice == 4)
                truckManagerFunctionality.printFutureTruckings();
            else if(choice == 5)
                truckManagerFunctionality.getDriversUsernames();
            else if(choice == 6)
                truckManagerFunctionality.getVehiclesRegistrationPlates();
            else if(choice == 7)
                truckManagerFunctionality.addTrucking();
            else if(choice == 8)
                truckManagerFunctionality.printBoardOfDriver();
            else if(choice == 9)
                truckManagerFunctionality.printTruckingHistoryOfDriver();
            else if(choice == 10)
                truckManagerFunctionality.printFutureTruckingsOfDriver();
            else if(choice == 11)
                truckManagerFunctionality.addVehicle();
            else if(choice == 12)
                truckManagerFunctionality.getRegisterCode();
            else if(choice == 13)
                truckManagerFunctionality.updatePassword();
            else if(choice == 14)
                truckManagerFunctionality.printBoardOfVehicle();
            else if(choice == 15)
                truckManagerFunctionality.printTruckingsHistoryOfVehicle();
            else if(choice == 16)
                truckManagerFunctionality.printFutureTruckingsOfVehicle();
            else if(choice == 17)
                truckManagerFunctionality.addSourcesToTrucking();
            else if(choice == 18)
                truckManagerFunctionality.updateSourcesOnTrucking();
            else if(choice == 19)
                truckManagerFunctionality.addDestinationToTrucking();
            else if(choice == 20)
                truckManagerFunctionality.updateDestinationsOnTrucking();
            else if(choice == 21)
                truckManagerFunctionality.updateDriverOnTrucking();
            else if(choice == 22)
                truckManagerFunctionality.updateVehicleOnTrucking();
            else if(choice == 23)
                truckManagerFunctionality.updateDateOnTrucking();
            else if(choice == 24)
                truckManagerFunctionality.moveProductsToTrucking();
            else if(choice == 25)
                truckManagerFunctionality.addProductToTrucking();
            else if(choice == 26) {
                truckManagerFunctionality.logOut();
                logedOut = true;
            }
            else
                System.out.println("You needed to select from the list...");
        }
    }
}
