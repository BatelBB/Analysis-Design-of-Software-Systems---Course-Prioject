package BusinessLayer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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

    public void addDriver(Driver driver) throws Exception {
        if (driver == null)
            throw new IllegalArgumentException("No driver entered");
        drivers.put(driver.username, driver);
    }

    public void addVehicle(Vehicle vehicle) throws Exception {
        if (vehicle == null)
            throw new IllegalArgumentException("No vehicle entered");
        vehicles.put(vehicle.getRegistationPlate(), vehicle);
    }

    public List<String> getDriversUsernames() {
        List<String> toReturn = new LinkedList<String>();
        for (String username : drivers.keySet()) {
            toReturn.add(username);
        }
        return toReturn;
    }

    public List<String> getVehiclesRegistrationPlates() {
        List<String> toReturn = new LinkedList<String>();
        for (String registrationPlate : vehicles.keySet()) {
            toReturn.add(registrationPlate);
        }
        return toReturn;
    }

    //TODO: add all the methods from the trucking board

}