package PresentaionLayer;

import ServiceLayer.Response;
import ServiceLayer.Service;

import java.util.List;

public class TruckManagerFunctionality extends UserFunctionality{

    public TruckManagerFunctionality() throws Exception {
        super();
    }

    public void printTruckingsBoard() {
        Response<String> response = service.printBoard();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }

    public void printTruckingsHistory() {
        Response<String> response = service.printTruckingsHistory();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }

    public void printFutureTruckings() {
        Response<String> response = service.printFutureTruckings();
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
    }

    public void printBoardOfDriver() {
        Response<List<String>> drivers = service.getDriversUsernames();
        if (drivers.ErrorOccured())
            System.out.println(drivers.getErrorMessage());
        if (drivers.getValue() == null | drivers.getValue().size() == 0)
            System.out.println("You have no drivers");
        else {
            System.out.println("Choose driver:");
            Main.printOptionsList(drivers.getValue());
            int choice = Main.getNumber();
            while (choice < 1 | choice > drivers.getValue().size()) {
                System.out.println("Enter number from the list");
                choice = Main.getNumber();
            }
            String driverUsername = drivers.getValue().get(choice);
            Response<String> response = service.printBoardOfDriver(driverUsername);
            if (response.ErrorOccured())
                System.out.println(response.getErrorMessage());
            else
                System.out.println(response.getValue());
        }
    }


}
