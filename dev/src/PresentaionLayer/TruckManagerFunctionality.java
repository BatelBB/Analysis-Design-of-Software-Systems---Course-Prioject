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
        String driverUsername = getDriverUsernameFromTheUser();
        if (driverUsername != null) {
        Response<String> response = service.printBoardOfDriver(driverUsername);
        if (response.ErrorOccured())
            System.out.println(response.getErrorMessage());
        else
            System.out.println(response.getValue());
        }
    }

    private String getDriverUsernameFromTheUser() {
        Response<List<String>> drivers = service.getDriversUsernames();
        if (drivers.ErrorOccured()) {
            System.out.println(drivers.getErrorMessage());
            return null;
        }
        if (drivers.getValue() == null | drivers.getValue().size() == 0) {
            System.out.println("You have no drivers");
            return null;
        }
        else {
            System.out.println("Choose driver:");
            int choice = Main.printOptionsList(drivers.getValue());
            return drivers.getValue().get(choice);
        }
    }


}
