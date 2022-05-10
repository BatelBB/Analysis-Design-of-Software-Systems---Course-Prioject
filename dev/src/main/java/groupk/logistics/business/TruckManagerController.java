package groupk.logistics.business;

import groupk.logistics.DataLayer.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TruckManagerController extends UserController{

    private Map<String, String> users;
    private Map<String,TruckManager> mapTM;
    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;
    private VehicleMapper vehicleMapper;
    private DriversMapper driversMapper;
    private TruckingMapper truckingMapper;
    private UserMapper userMapper;
    private Truckings_DestsMapper truckings_destsMapper;
    private Truckings_SourcesMapper sourcesMapper;
    private Truckings_ProductsMapper productsMapper;



    public static TruckManagerController getInstance() throws Exception {
        if (singletonTruckManagerControllerInstance == null)
            singletonTruckManagerControllerInstance = new TruckManagerController();
        return singletonTruckManagerControllerInstance;
    }

    private TruckManagerController() throws Exception {
        super(null);
        truckingIdCounter = 1;
        vehicleMapper = new VehicleMapper();
        driversMapper = new DriversMapper();
        truckingMapper = new TruckingMapper();
        userMapper = new UserMapper();
        truckings_destsMapper = new Truckings_DestsMapper();
        sourcesMapper= new Truckings_SourcesMapper();
        productsMapper = new Truckings_ProductsMapper();
    }

    public void reserForTests()
    {
        truckingIdCounter = 1;
    }




    public void addVehicle(String lisence, String registrationPlate, String model, int weight, int maxWeight) throws Exception {
            Vehicle newVehicle = new Vehicle(lisence, registrationPlate, model, weight, maxWeight);
            boolean success = vehicleMapper.addVehicle(lisence, registrationPlate, model, weight, maxWeight);
            if(success)vehicleMapper.addVehicle(newVehicle);
    }

    public List<String> getDriversUsernames() throws Exception { return driversMapper.selectTMDrivers(getActiveUser().getUsername()); }

    public List<String> getVehiclesRegistrationPlates() throws Exception { return vehicleMapper.selectAll(); }

//    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
//        synchronized (getActiveUser()) {
//            checkIfActiveUserIsTruckManager();
//            ((TruckManager)getActiveUser()).addTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes);
//            truckingIdCounter++;
//        }
//    }



    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
        boolean checkTrucking = ((TruckManager)getActiveUser()).checkTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes);
        if(checkTrucking) {
            User driver = userMapper.getUser(driverUsername);
            if(driver==null||driver.getRole()!=Role.driver) throw new Exception("The driver username is not ok");
            Vehicle vehicle = vehicleMapper.getVehicle(registrationPlateOfVehicle);
            if(vehicle==null)  throw new Exception("The registration plate username is not ok");
            List<Site> sources_ = checkSites(sources);
            List<Site> destinations_ = checkSites(destinations);
            List<ProductForTrucking> productForTruckings = Trucking.productForTruckings(products);
            Trucking trucking = new Trucking(truckingIdCounter,vehicle,date,(Driver)driver,sources,destinations,productForTruckings,hours,minutes);
            boolean added = truckingMapper.addTrucking(truckingIdCounter,getActiveUser().getUsername(),registrationPlateOfVehicle,driverUsername,date,hours,minutes);
            sourcesMapper.addTruckingSources(truckingIdCounter,sources_);
            truckings_destsMapper.addTruckingDestinations(truckingIdCounter,destinations_);
            productsMapper.addTruckingProducts(truckingIdCounter,productForTruckings);
            if(added) {
                truckingMapper.addTrucking(trucking);
                truckingIdCounter++;
            }
        }
    }

    private List<Site> checkSites(List<List<String>> Sites)
    {
        List<Site> sites = new LinkedList<>();
        for(List<String> site : Sites)
            sites.add(new Site(site.get(0),site.get(1),site.get(2),site.get(3),Integer.parseInt(site.get(4)),Integer.parseInt(site.get(5)),Integer.parseInt(site.get(6)),site.get(7)));
        return sites;
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
