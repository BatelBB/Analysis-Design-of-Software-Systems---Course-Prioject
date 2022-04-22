package BusinessLayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserController {

    private Map<String, User> users;
    private Map<String, Driver> drivers;
    private Map<String, TruckManager> truckManagers;
    private Map<String, TruckManager> truckManagersHashCodes;
    private static UserController singletonUserControllerInstance = null;
    protected User activeUser;
    private final int MIN_USERNAME_LENGTH = 3;
    private final int MAX_USERNAME_LENGTH = 12;
    private String CODE_TRUCK_MANAGER = "tm1234tm";
    private Map<String, String> UNIQUE_DRIVER_CODE_OF_TM; //key: hashcode, value: tmUsername
    private DriverController driverController;
    private TruckManagerController truckManagerController;
    private int currentUserState;
    private Driver activeDriver;
    private TruckManager activeTruckManager;


    private UserController() throws Exception {
        currentUserState = 0;
        driverController = new DriverController();
        truckManagerController = new TruckManagerController();
        activeUser = loggedOutUser;
        activeDriver = null;
        activeDriver = null;
        users = new ConcurrentHashMap<String, User>();
        drivers = new ConcurrentHashMap<String, Driver>();
        truckManagers = new ConcurrentHashMap<String, TruckManager>();
        drivers = new ConcurrentHashMap<String, Driver>();
        truckManagers = new ConcurrentHashMap<String, TruckManager>();
        UNIQUE_DRIVER_CODE_OF_TM = new ConcurrentHashMap<String, String>();
        truckManagersHashCodes = new ConcurrentHashMap<String, TruckManager>();

    }

    public static UserController getInstance() throws Exception {
        if (singletonUserControllerInstance == null)
            singletonUserControllerInstance = new UserController();
        return singletonUserControllerInstance;
    }

    public synchronized boolean registerUser(String name, String username, String password, Role role, String code) throws Exception {
        synchronized (activeUser) {
            if (role == null)
                throw new Exception("Please select role");
            if (!validateUsernameToRegister(username))
                throw new Exception("Username is not valid");
            if(users.containsKey(username))
                throw new Exception("Username Exists");
            else if (role == Role.truckingManager) {
                if (code != CODE_TRUCK_MANAGER)
                    throw new IllegalArgumentException("Sorry, the code is not valid");
                TruckManager newUser = new TruckManager(name, username, password, null);
                UNIQUE_DRIVER_CODE_OF_TM.put(String.valueOf(newUser.hashCode()), username);
                truckManagers.put(username,newUser);
                truckManagersHashCodes.put(String.valueOf(newUser.hashCode()),newUser);
                users.put(username, newUser);
            }
            else if (role == Role.driver) {
                TruckManager truckManagerOfTheDriver = truckManagersHashCodes.get(code);
                Driver newUser = new Driver(name, username, password, truckManagerOfTheDriver);
                truckManagerOfTheDriver.addDriver(newUser);
                drivers.put(username,newUser);
                users.put(username, newUser);
            }
            else
                throw new Exception("Sorry, we can not yet open a user for this type of employee");
            return true;
        }
    }

    public boolean login(String username, String password) throws Exception {
        synchronized (activeUser) {
            if (!(currentUserState==0))
                throw new IllegalArgumentException("There is a user already connected to the system");
            if (username == null)
                throw new IllegalArgumentException("Please enter valid username");
            User user = users.get(username);
            if (user == null)
                throw new IllegalArgumentException("Sorry but there's no user with that username");
            if(users.get(username).login(password)) {
                if(truckManagers.containsKey(username) && truckManagers.get(username).checkPassword(password)) {
                    activeTruckManager = truckManagers.get(username);
                    currentUserState = 1;
                }
                else
                {
                    activeDriver = drivers.get(username);
                    currentUserState = 2;
                }
                activeUser = user;
                return true;
            }
            return false;
        }
    }

    public boolean logout() throws Exception {
        synchronized (activeUser) {
            if (activeUser.hashCode() == loggedOutUser.hashCode())
                throw new Exception("There is no active user in the system");
            if(activeUser.logout()) {
                currentUserState = 0;
                activeUser = loggedOutUser;
                activeDriver = null;
                activeTruckManager = null;
                return true;
            }
        }
        return false;
    }

    public boolean updatePassword(String newPassword) throws Exception {
        synchronized (activeUser) {
            if (currentUserState==0)
                throw new Exception("There is no active user in the system");
            return activeUser.updatePassword(newPassword);
        }
    }

    private boolean validateUsernameToRegister(String username)
    {
        if (username == null)
            return false;
        if (username.length() < MIN_USERNAME_LENGTH)
            throw new IllegalArgumentException("The minimum username length should be at least 3 characters");
        if (username.length() > MAX_USERNAME_LENGTH)
            throw new IllegalArgumentException("The maximum username length should be up to 12 characters");
        for (int i = 0; i < username.length(); i++) {
            if(!(Character.isLetter(username.charAt(i)) | Character.isDigit(username.charAt(i))))
                throw new IllegalArgumentException("Username can only contain letters and numbers");
        }
        if (users.containsKey(username))
            throw new IllegalArgumentException("Username is already exist. try another one.");
        return true;
    }



    public String printMyTruckings() throws Exception {
        return driverController.printMyTruckings(activeDriver);
    }

    public String printMyTruckingsHistory() throws Exception {
        return driverController.printMyTruckingsHistory(activeDriver);

    }

    public String printMyFutureTruckings() throws Exception {
        return driverController.printMyFutureTruckings(activeDriver);
    }

    public void addLicense(DLicense license) throws Exception {
         driverController.addLicense(activeDriver,license);
    }

    public void addLicenses( List<DLicense> licenses) throws Exception {
         driverController.addLicenses(activeDriver,licenses);
    }

    public void setWeightForTrucking(int truckingId, int weight) throws Exception {
        driverController.setWeightForTrucking(activeDriver,truckingId,weight);
    }

    public void removeLicense(DLicense license) throws Exception {
        driverController.removeLicense(activeDriver,license);
    }


    public void addVehicle(DLicense lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
        truckManagerController.addVehicle(activeTruckManager,lisence, registrationPlate, model, weight, maxWeight);
    }

    public List<String> getDriversUsernames() throws Exception {
        return truckManagerController.getDriversUsernames(activeTruckManager);
    }

    public List<String> getVehiclesRegistrationPlates() throws Exception {
        return truckManagerController.getVehiclesRegistrationPlates(activeTruckManager);
    }

    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<Site> sources, List<Site> destinations, List<ProductForTrucking> products,long hours,long minutes) throws Exception {
         truckManagerController.addTrucking(activeTruckManager,registrationPlateOfVehicle,date,driverUsername,sources,destinations,products,hours,minutes);
    }

    public void removeTrucking(int truckingId) throws Exception {
        truckManagerController.removeTrucking(activeTruckManager,truckingId);
    }

    public String printBoard() throws Exception {
        return truckManagerController.printBoard(activeTruckManager);
    }

    public String printTruckingsHistory() throws Exception {
        return truckManagerController.printTruckingsHistory(activeTruckManager);
    }

    public String printFutureTruckings() throws Exception {
        return truckManagerController.printFutureTruckings(activeTruckManager);
    }

    public String printBoardOfDriver(String driverUsername) throws Exception {
        return truckManagerController.printBoardOfDriver(activeTruckManager,driverUsername);
    }

    public String printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        return truckManagerController.printTruckingsHistoryOfDriver(activeTruckManager,driverUsername);
    }

    public String printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        return truckManagerController.printFutureTruckingsOfDriver(activeTruckManager,driverUsername);
    }

    public String printBoardOfVehicle(String registrationPlate) throws Exception {
        return truckManagerController.printBoardOfVehicle(activeTruckManager,registrationPlate);
    }

    public String printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        return truckManagerController.printTruckingsHistoryOfVehicle(activeTruckManager,registrationPlate);
    }

    public String printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        return truckManagerController.printFutureTruckingsOfVehicle(activeTruckManager,registrationPlate);
    }

    public void addSourcesToTrucking(int truckingId, List<Site> sources) throws Exception {
         truckManagerController.addSourcesToTrucking(activeTruckManager,truckingId,sources);
    }

    public void addDestinationToTrucking(int truckingId, List<Site> destinations) throws Exception {
        truckManagerController.addDestinationToTrucking(activeTruckManager, truckingId, destinations);
    }

    public void addProductToTrucking(int truckingId, ProductForTrucking productForTrucking) throws Exception {
        truckManagerController.addProductToTrucking(activeTruckManager,truckingId,productForTrucking);
    }

    public void updateSourcesOnTrucking(int truckingId, List<Site> sources) throws Exception {
        truckManagerController.updateSourcesOnTrucking(activeTruckManager,truckingId,sources);
    }

    public void updateDestinationsOnTrucking(int truckingId, List<Site> destinations) throws Exception {
        truckManagerController.updateDestinationsOnTrucking(activeTruckManager,truckingId,destinations);
    }

    public void moveProductsToTrucking(int truckingId, Products productSKU) throws Exception {
        truckManagerController.moveProductsToTrucking(activeTruckManager,truckingId,productSKU);
    }

    public void updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        truckManagerController.updateVehicleOnTrucking(activeTruckManager,truckingId,registrationPlateOfVehicle);
    }

    public void updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        truckManagerController.updateDriverOnTrucking(activeTruckManager,truckingId,driverUsername);
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        truckManagerController.updateDateOnTrucking(activeTruckManager,truckingId,date);
    }

    public synchronized String getRegisterCode() {
        return activeTruckManager.getRegisterCode();
    }


    protected final User loggedOutUser = new TruckManager(null, null, null, null);
}

