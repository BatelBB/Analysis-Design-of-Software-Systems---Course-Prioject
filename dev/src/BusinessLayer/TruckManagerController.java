package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckManagerController extends UserController{

    private Map<String, String> users;
    private Map<String,TruckManager> mapTM;
    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;

    public static TruckManagerController getInstance() {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    private TruckManagerController() {
        truckingIdCounter = 1;
    }

    public void addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            Vehicle newVehicle = new Vehicle(lisence, registrationPlate, model, weight, maxWeight);
            ((TruckManager)activeUser).addVehicle(newVehicle);
        }
    }

    public List<String> getDriversUsernames() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).getDriversUsernames();
        }
    }

    public List<String> getVehiclesRegistrationPlates() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).getVehiclesRegistrationPlates();
        }
    }

    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).addTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products);
            truckingIdCounter++;
        }
    }

    public void removeTrucking(int truckingId) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).removeTrucking(truckingId);
        }
    }

    public String printBoard() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printBoard();
        }
    }

    public String printTruckingsHistory() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printTruckingsHistory();
        }
    }

    public String printFutureTruckings() throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printFutureTruckings();
        }
    }

    public String printBoardOfDriver(String driverUsername) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printBoardOfDriver(driverUsername);
        }
    }

    public String printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printTruckingsHistoryOfDriver(driverUsername);
        }
    }

    public String printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printFutureTruckingsOfDriver(driverUsername);
        }
    }

    public String printBoardOfVehicle(String registrationPlate) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printBoardOfVehicle(registrationPlate);
        }
    }

    public String printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printTruckingsHistoryOfVehicle(registrationPlate);
        }
    }

    public String printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)activeUser).printFutureTruckingsOfVehicle(registrationPlate);
        }
    }

    public void addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).addSourcesToTrucking(truckingId, sources);
        }
    }

    public void addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).addDestinationsToTrucking(truckingId, destinations);
        }
    }

    public void addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).addProductsToTrucking(truckingId, productForTrucking);
        }
    }

    public void updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).updateSourcesOnTrucking(truckingId, sources);
        }
    }

    public void updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).updateDestinationsOnTrucking(truckingId, destinations);
        }
    }

    public void moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).moveProductsToTrucking(truckingId, productSKU);
        }
    }

    public void updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).updateVehicleOnTrucking(truckingId, registrationPlateOfVehicle);
        }
    }

    public void updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).updateDriverOnTrucking(truckingId, driverUsername);
        }
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        synchronized (activeUser) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)activeUser).updateDateOnTrucking(truckingId, date);
        }
    }

    private void checkIfActiveUserIsTruckManager() throws Exception {
        if (activeUser == null)
            throw new Exception("There is no user connected");
        if (activeUser.getRole() != Role.truckingManager | !(activeUser instanceof TruckManager))
            throw new Exception("Oops, you are not a truck manager");
    }

}
