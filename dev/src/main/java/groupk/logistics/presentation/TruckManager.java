package groupk.logistics.presentation;

public class TruckManager extends User{
    public TruckManager() {
    }

    public void act() throws Exception {
        boolean logedOut = false;
        while (!logedOut)
        {
            System.out.println();
            System.out.println("Enter 1  to get information about your truckings");
            System.out.println("Enter 2  to get information about your drivers and update data about them");
            System.out.println("Enter 3  to get information about your vehicles and update data about them");
            System.out.println("Enter 4  to update truckings");
            System.out.println("Enter 5  to get register code");
            System.out.println("Enter 6  to update password");
            System.out.println("Enter 7  to logout");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                getInformationAboutTruckings();
            else if(choice == 2)
                getInformationAboutDrivers();
            else if(choice == 3)
                getInformationAboutVehicles();
            else if(choice == 4)
                updateTruckings();
            else if(choice == 5)
                truckManagerFunctionality.getRegisterCode();
            else if(choice == 6)
                truckManagerFunctionality.updatePassword();
            else if(choice == 7) {
                truckManagerFunctionality.logOut();
                logedOut = true;
            }
            else
                System.out.println("You needed to select from the list...");
        }
    }

    private void getInformationAboutTruckings() throws Exception {
        boolean found = false;
        while (!found) {
            found = true;
            System.out.println();
            System.out.println("Enter 1  to print your board");
            System.out.println("Enter 2  to print truckings history");
            System.out.println("Enter 3  to print future truckings");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                truckManagerFunctionality.printTruckingsBoard();
            else if(choice == 2)
                truckManagerFunctionality.printTruckingsHistory();
            else if(choice == 3)
                truckManagerFunctionality.printFutureTruckings();
            else {
                System.out.println("You needed to select from the list...");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String answer = Main.getChoiceFromArray(new String[]{"Yes", "No"});
                if (answer.equals("Yes"))
                    found = false;
            }
        }
    }

    private void getInformationAboutDrivers() throws Exception {
        boolean found = false;
        while (!found) {
            found = true;
            System.out.println();
            System.out.println("Enter 1  to print your drivers usernames");
            System.out.println("Enter 2  to print a board of one of your drivers");
            System.out.println("Enter 3  to print truckings history of a driver");
            System.out.println("Enter 4  to print future trucking of a driver");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                truckManagerFunctionality.getDriversUsernames();
            else if(choice == 2)
                truckManagerFunctionality.printBoardOfDriver();
            else if(choice == 3)
                truckManagerFunctionality.printTruckingHistoryOfDriver();
            else if(choice == 4)
                truckManagerFunctionality.printFutureTruckingsOfDriver();
            else {
                System.out.println("You needed to select from the list...");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String answer = Main.getChoiceFromArray(new String[]{"Yes", "No"});
                if (answer.equals("Yes"))
                    found = false;
            }
        }
    }

    private void getInformationAboutVehicles() throws Exception {
        boolean found = false;
        while (!found) {
            found = true;
            System.out.println();
            System.out.println();
            System.out.println("Enter 1  to print the vehicles registration plates");
            System.out.println("Enter 2  to add a vehicle");
            System.out.println("Enter 3  to print a board of one of your vehicles");
            System.out.println("Enter 4  to print truckings history of a vehicle");
            System.out.println("Enter 5  to print future trucking of a vehicle");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                truckManagerFunctionality.getVehiclesRegistrationPlates();
            else if(choice == 2)
                truckManagerFunctionality.addVehicle();
            else if(choice == 3)
                truckManagerFunctionality.printBoardOfVehicle();
            else if(choice == 4)
                truckManagerFunctionality.printTruckingsHistoryOfVehicle();
            else if(choice == 5)
                truckManagerFunctionality.printFutureTruckingsOfVehicle();
            else {
                System.out.println("You needed to select from the list...");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String answer = Main.getChoiceFromArray(new String[]{"Yes", "No"});
                if (answer.equals("Yes"))
                    found = false;
            }
        }
    }

    private void updateTruckings() throws Exception {
        boolean found = false;
        while (!found) {
            found = true;
            System.out.println();
            System.out.println();
            System.out.println("Enter 1  to remove Trucking");
            System.out.println("Enter 2  to add trucking");
            System.out.println("Enter 3  to add sources to a trucking");
            System.out.println("Enter 4  to update sources of a trucking");
            System.out.println("Enter 5  to add sources to a trucking");
            System.out.println("Enter 6  to update destinations of a trucking");
            System.out.println("Enter 7  to update driver on a trucking");
            System.out.println("Enter 8  to update vehicle on a trucking");
            System.out.println("Enter 9  to update date on a trucking");
            System.out.println("Enter 10 to remove a product from a trucking");
            System.out.println("Enter 11 to add a product to a trucking");
            int choice = Main.getNumber();
            TruckManagerFunctionality truckManagerFunctionality = new TruckManagerFunctionality();
            if(choice == 1)
                truckManagerFunctionality.removeTrucking();
            else if(choice == 2)
                truckManagerFunctionality.addTrucking();
            else if(choice == 3)
                truckManagerFunctionality.addSourcesToTrucking();
            else if(choice == 4)
                truckManagerFunctionality.updateSourcesOnTrucking();
            else if(choice == 5)
                truckManagerFunctionality.addDestinationToTrucking();
            else if(choice == 6)
                truckManagerFunctionality.updateDestinationsOnTrucking();
            else if(choice == 7)
                truckManagerFunctionality.updateDriverOnTrucking();
            else if(choice == 8)
                truckManagerFunctionality.updateVehicleOnTrucking();
            else if(choice == 9)
                truckManagerFunctionality.updateDateOnTrucking();
            else if(choice == 10)
                truckManagerFunctionality.moveProductsToTrucking();
            else if(choice == 11)
                truckManagerFunctionality.addProductToTrucking();
            else {
                System.out.println("You needed to select from the list...");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String answer = Main.getChoiceFromArray(new String[]{"Yes", "No"});
                if (answer.equals("Yes"))
                    found = false;
            }
        }
    }
}
