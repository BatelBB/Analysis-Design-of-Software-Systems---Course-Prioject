package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TruckManager extends User {

    private TruckingsBoard truckingsBoard;
    private Map<String, Driver> drivers; //saves the drivers by their usernames
    private Map<String, Vehicle> vehicles; //saves the vehicles by their registration plate

    public TruckManager(String name, String username, String password) throws Exception {
        super(name, username, password);
        this.role = Role.truckingManager;
        this.truckingsBoard = new TruckingsBoard(this);
        drivers = new ConcurrentHashMap<String, Driver>();
        vehicles = new ConcurrentHashMap<String, Vehicle>();
    }

    public synchronized void addDriver(Driver driver) throws Exception {
        if (driver == null)
            throw new IllegalArgumentException("No driver entered");
        drivers.put(driver.username, driver);
    }

    public synchronized void addVehicle(Vehicle vehicle) throws Exception {
        if (vehicle == null)
            throw new IllegalArgumentException("No vehicle entered");
        vehicles.put(vehicle.getRegistationPlate(), vehicle);
    }

    public synchronized List<String> getDriversUsernames() {
        List<String> toReturn = new LinkedList<String>();
        for (String username : drivers.keySet()) {
            toReturn.add(username);
        }
        return toReturn;
    }

    public synchronized List<String> getVehiclesRegistrationPlates() {
        List<String> toReturn = new LinkedList<String>();
        for (String registrationPlate : vehicles.keySet()) {
            toReturn.add(registrationPlate);
        }
        return toReturn;
    }

    public synchronized void addTrucking(Trucking trucking) throws Exception {
        if (trucking == null)
            throw new NullPointerException("The trucking is empty");
        if (!drivers.containsKey(trucking.getDriver().getUsername()))
            throw new IllegalArgumentException("The driver is not under the current truck manager");
        if (!vehicles.containsKey(trucking.getVehicle().getRegistationPlate()))
            throw new IllegalArgumentException("The vehicle is not under the current truck manager");
        //TODO: add to the vehicle truckings
        trucking.getDriver().addTrucking(trucking);
        truckingsBoard.addTrucking(trucking);
    }

    public synchronized void removeTrucking(int truckingId) {
        truckingsBoard.removeTrucking(truckingId);
    }

    public synchronized String printBoard() {
        return truckingsBoard.printBoard();
    }

    public synchronized String printDoneTruckings() {
        return truckingsBoard.printDoneTruckings();
    }

    public synchronized String printFutureTruckings() {
        return truckingsBoard.printFutureTruckings();
    }

    public synchronized void addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
        truckingsBoard.addSourcesToTrucking(truckingId, sources);
    }

    public synchronized void addDestinationsToTrucking(int truckingId, List<Site> destinations) throws Exception {
        truckingsBoard.addDestinationsToTrucking(truckingId, destinations);
    }

    public synchronized void addProductsToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        truckingsBoard.addProductsToTrucking(truckingId, productForTrucking);
    }

    public synchronized void updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        truckingsBoard.updateSourcesOnTrucking(truckingId, sources);
    }

    public synchronized void updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        truckingsBoard.updateDestinationsOnTrucking(truckingId, destinations);
    }

    public synchronized void moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        truckingsBoard.moveProductsToTrucking(truckingId, productSKU);
    }

    public synchronized void updateVehicleOnTrucking(int truckingId, Vehicle vehicle) throws Exception {
        truckingsBoard.updateVehicleOnTrucking(truckingId, vehicle);
    }

    public synchronized void updateDriverOnTrucking(int truckingId, Driver driver) throws Exception {
        truckingsBoard.updateDriverOnTrucking(truckingId, driver);
    }

    public synchronized void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        truckingsBoard.updateDateOnTrucking(truckingId, date);
    }

}