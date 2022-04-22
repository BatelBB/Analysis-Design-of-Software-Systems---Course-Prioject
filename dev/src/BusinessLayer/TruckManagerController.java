package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckManagerController {

    private Map<String, String> users;
    private Map<String,TruckManager> mapTM;
    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;

    public static TruckManagerController getInstance() throws Exception {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    TruckManagerController() {
        truckingIdCounter = 1;
    }

    public void addVehicle(TruckManager truckManager,DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        Vehicle newVehicle = new Vehicle(lisence, registrationPlate, model, weight, maxWeight);
        truckManager.addVehicle(newVehicle);
    }

    public List<String> getDriversUsernames(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.getDriversUsernames();
    }

    public List<String> getVehiclesRegistrationPlates(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.getVehiclesRegistrationPlates();
    }

    public void addTrucking(TruckManager truckManager,String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hours, long minutes) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.addTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products,hours,minutes);
        truckingIdCounter++;
    }

    public void removeTrucking(TruckManager truckManager,int truckingId) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.removeTrucking(truckingId);
    }

    public String printBoard(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printBoard();
    }

    public String printTruckingsHistory(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printTruckingsHistory();
    }

    public String printFutureTruckings(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printFutureTruckings();
    }

    public String printBoardOfDriver(TruckManager truckManager,String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printBoardOfDriver(driverUsername);
    }

    public String printTruckingsHistoryOfDriver(TruckManager truckManager,String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printTruckingsHistoryOfDriver(driverUsername);
    }

    public String printFutureTruckingsOfDriver(TruckManager truckManager,String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printFutureTruckingsOfDriver(driverUsername);
    }

    public String printBoardOfVehicle(TruckManager truckManager,String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printBoardOfVehicle(registrationPlate);
    }

    public String printTruckingsHistoryOfVehicle(TruckManager truckManager,String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printTruckingsHistoryOfVehicle(registrationPlate);
    }

    public String printFutureTruckingsOfVehicle(TruckManager truckManager,String registrationPlate) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.printFutureTruckingsOfVehicle(registrationPlate);
    }

    public void addSourcesToTrucking(TruckManager truckManager,int truckingId, List<Site> sources) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.addSourcesToTrucking(truckingId, sources);
    }

    public void addDestinationToTrucking(TruckManager truckManager,int truckingId, List<Site> destinations) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.addDestinationsToTrucking(truckingId, destinations);
    }

    public void addProductToTrucking(TruckManager truckManager,int truckingId, ProductForTrucking productForTrucking) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.addProductsToTrucking(truckingId, productForTrucking);
    }

    public void updateSourcesOnTrucking(TruckManager truckManager,int truckingId, List<Site> sources) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.updateSourcesOnTrucking(truckingId, sources);
    }

    public void updateDestinationsOnTrucking(TruckManager truckManager,int truckingId, List<Site> destinations) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.updateDestinationsOnTrucking(truckingId, destinations);
    }

    public void moveProductsToTrucking(TruckManager truckManager,int truckingId, Products productSKU) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.moveProductsToTrucking(truckingId, productSKU);
    }

    public void updateVehicleOnTrucking(TruckManager truckManager,int truckingId, String registrationPlateOfVehicle) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.updateVehicleOnTrucking(truckingId, registrationPlateOfVehicle);
    }

    public void updateDriverOnTrucking(TruckManager truckManager,int truckingId, String driverUsername) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.updateDriverOnTrucking(truckingId, driverUsername);
    }

    public void updateDateOnTrucking(TruckManager truckManager,int truckingId, LocalDateTime date) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        truckManager.updateDateOnTrucking(truckingId, date);
    }

    public String getMyRegisterCode(TruckManager truckManager) throws Exception {
        checkIfActiveUserIsTruckManager(truckManager);
        return truckManager.getRegisterCode();
    }

    private void checkIfActiveUserIsTruckManager(TruckManager truckManager) throws Exception {
        if (truckManager==null)
            throw new Exception("There is no user connected");
        if (truckManager.getRole() != Role.truckingManager)
            throw new Exception("Oops, you are not a truck manager");
    }
}

