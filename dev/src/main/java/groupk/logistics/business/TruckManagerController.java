package BusinessLayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TruckManagerController extends UserController{

    private Map<String, String> users;
    private Map<String,TruckManager> mapTM;
    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;

    public static TruckManagerController getInstance() throws Exception {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    private TruckManagerController() throws Exception {
        super(null);
        truckingIdCounter = 1;
    }

    public void reserForTests()
    {
        truckingIdCounter = 1;
    }


    public void addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            Vehicle newVehicle = new Vehicle(lisence, registrationPlate, model, weight, maxWeight);
            ((TruckManager)getActiveUser()).addVehicle(newVehicle);
        }
    }

    public List<String> getDriversUsernames() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).getDriversUsernames();
        }
    }

    public List<String> getVehiclesRegistrationPlates() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).getVehiclesRegistrationPlates();
        }
    }

    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).addTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes);
            truckingIdCounter++;
        }
    }

    public void removeTrucking(int truckingId) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).removeTrucking(truckingId);
        }
    }

    public String printBoard() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printBoard();
        }
    }

    public String printTruckingsHistory() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printTruckingsHistory();
        }
    }

    public String printFutureTruckings() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printFutureTruckings();
        }
    }

    public String printBoardOfDriver(String driverUsername) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printBoardOfDriver(driverUsername);
        }
    }

    public String printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printTruckingsHistoryOfDriver(driverUsername);
        }
    }

    public String printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printFutureTruckingsOfDriver(driverUsername);
        }
    }

    public String printBoardOfVehicle(String registrationPlate) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printBoardOfVehicle(registrationPlate);
        }
    }

    public String printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printTruckingsHistoryOfVehicle(registrationPlate);
        }
    }

    public String printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return ((TruckManager)getActiveUser()).printFutureTruckingsOfVehicle(registrationPlate);
        }
    }

    public void addSourcesToTrucking(int truckingId, List<List<String>> sources) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).addSourcesToTrucking(truckingId, sources);
        }
    }

    public void addDestinationToTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).addDestinationsToTrucking(truckingId, destinations);
        }
    }

    public void addProductToTrucking(int truckingId, String pruductName,int quantity) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).addProductsToTrucking(truckingId, pruductName,quantity);
        }
    }



    public void updateSourcesOnTrucking(int truckingId, List<List<String>> sources) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).updateSourcesOnTrucking(truckingId, sources);
        }
    }

    public void updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).updateDestinationsOnTrucking(truckingId, destinations);
        }
    }

    public void moveProductsToTrucking(int truckingId, String productSKU) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).moveProductsToTrucking(truckingId, productSKU);
        }
    }

    public void updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).updateVehicleOnTrucking(truckingId, registrationPlateOfVehicle);
        }
    }

    public void updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).updateDriverOnTrucking(truckingId, driverUsername);
        }
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            ((TruckManager)getActiveUser()).updateDateOnTrucking(truckingId, date);
        }
    }

    public String getRegisterCode() throws Exception {
        synchronized (getActiveUser()) {
            checkIfActiveUserIsTruckManager();
            return String.valueOf(getActiveUser().hashCode());
        }
    }

    private void checkIfActiveUserIsTruckManager() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new Exception("There is no user connected");
        if (getActiveUser().getRole() != Role.truckingManager | !(getActiveUser() instanceof TruckManager))
            throw new Exception("Oops, you are not a truck manager");
    }

}
