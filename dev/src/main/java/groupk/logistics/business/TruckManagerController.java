package groupk.logistics.business;

import groupk.logistics.DataLayer.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TruckManagerController extends UserController{

    private static TruckManagerController singletonTruckManagerControllerInstance = null;
    private int truckingIdCounter;
    private VehicleMapper vehicleMapper;
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

    public List<String> getDriversUsernames() throws Exception {
        return null; //TODO
    }

    public List<String> getVehiclesRegistrationPlates() throws Exception {
        return vehicleMapper.selectAll();
    }

    public void addTrucking(String registrationPlateOfVehicle, LocalDateTime date, String driverUsername, List<List<String>> sources, List<List<String>> destinations, List<Map<String,Integer>> products, long hours, long minutes) throws Exception {
        boolean checkTrucking = (TruckManager.checkTrucking(truckingIdCounter, registrationPlateOfVehicle, date, driverUsername, sources, destinations, products, hours, minutes));
        if (!truckingMapper.checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        truckingMapper.checkConflicts(driverUsername, registrationPlateOfVehicle, date, hours, minutes);
        if(checkTrucking) {
            List<Site> sources_ = checkSites(sources);
            List<Site> destinations_ = checkSites(destinations);
            List<ProductForTrucking> productForTruckings = Trucking.productForTruckings(products);
            TruckingDTO trucking = new TruckingDTO(truckingIdCounter,date,getActiveUser().getUsername(),driverUsername,registrationPlateOfVehicle,hours,minutes,0);
            boolean added = truckingMapper.addTrucking(trucking);
            sourcesMapper.addTruckingSources(truckingIdCounter,sources_); //to fix
            truckings_destsMapper.addTruckingDestinations(truckingIdCounter,destinations_);
            productsMapper.addTruckingProducts(truckingIdCounter,productForTruckings);
            if(added)
                truckingIdCounter++;
        }
    }

    private List<Site> checkSites(List<List<String>> Sites) throws Exception {
        List<Site> sites = new LinkedList<>();
        for(List<String> site : Sites)
            sites.add(new Site(site.get(0),site.get(1),site.get(2),site.get(3),Integer.parseInt(site.get(4)),Integer.parseInt(site.get(5)),Integer.parseInt(site.get(6)),site.get(7)));
        return sites;
    }


    public void removeTrucking(int truckingId) throws Exception {
        sourcesMapper.removeTrucking(truckingId);
        truckings_destsMapper.removeTrucking(truckingId);
        productsMapper.removeTrucking(truckingId);
        if(!truckingMapper.removeTrucking(truckingId))
            throw new Exception("seem like there is no trucking with that id");
    }

    public String printBoard() throws Exception {
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerBoard(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistory() throws Exception {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerHistoryTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckings() throws Exception {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getTruckManagerFutureTruckings(getActiveUser().getUsername());
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printBoardOfDriver(String driverUsername) throws Exception {
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverBoard(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistoryOfDriver(String driverUsername) throws Exception {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverHistoryTruckings(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckingsOfDriver(String driverUsername) throws Exception {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getDriverFutureTruckings(driverUsername);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printBoardOfVehicle(String registrationPlate) throws Exception {
        String toReturn = "TRUCKINGS BOARD\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleBoard(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printTruckingsHistoryOfVehicle(String registrationPlate) throws Exception {
        String toReturn = "            TRUCKINGS HISTORY\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleHistoryTruckings(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public String printFutureTruckingsOfVehicle(String registrationPlate) throws Exception {
        String toReturn = "            FUTURE TRUCKINGS\n\n";
        List<TruckingDTO> truckings = truckingMapper.getVehicleFutureTruckings(registrationPlate);
        if (truckings.size() == 0 | truckings == null) {
            toReturn += "[empty]";
            return toReturn;
        }
        for (TruckingDTO trucking : truckings)
            toReturn += printTrucking(trucking);
        return toReturn;
    }

    public List<String> addSourcesToTrucking(int truckingId, List<List<String>> sources) throws Exception {
        List<Site> sources_ = checkSites(sources);
        List<String> exceptions = sourcesMapper.addTruckingSources(truckingId, sources_);
        if(exceptions.size() == sources_.size())
            throw new Exception("Oops, We could not add any source");
        return exceptions;
    }

    public List<String> addDestinationToTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        List<Site> destinations_ = checkSites(destinations);
        List<String> exceptions = truckings_destsMapper.addTruckingDestinations(truckingId, destinations_);
        if(exceptions.size() == destinations_.size())
            throw new Exception("Oops, We could not add any destination");
        return exceptions;
    }

    public void addProductToTrucking(int truckingId, String pruductName,int quantity) throws Exception {
        //TODO
    }

    public void updateSourcesOnTrucking(int truckingId, List<List<String>> sources) throws Exception {
        //TODO
    }

    public void updateDestinationsOnTrucking(int truckingId, List<List<String>> destinations) throws Exception {
        //TODO
    }

    public void moveProductsToTrucking(int truckingId, String productSKU) throws Exception {
        //TODO
    }

    public void updateVehicleOnTrucking(int truckingId, String registrationPlateOfVehicle) throws Exception {
        String driverUsername = truckingMapper.getDriverUsernameOfTrucking(truckingId);
        if (!truckingMapper.checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        if (!truckingMapper.updateVehicle(truckingId, registrationPlateOfVehicle))
            throw new IllegalArgumentException("No change of vehicle was made to order: " + truckingId + ". It maybe the same vehicle of before the change.");
    }

    public void updateDriverOnTrucking(int truckingId, String driverUsername) throws Exception {
        String registrationPlateOfVehicle = truckingMapper.getRegistrationPlateOfTrucking(truckingId);
        if (!truckingMapper.checkDriverLicenseMatch(driverUsername, registrationPlateOfVehicle))
            throw new IllegalArgumentException("Oops, the driver does not have a driver's license compatible with this vehicle");
        if (!truckingMapper.updateVehicle(truckingId, registrationPlateOfVehicle))
            throw new IllegalArgumentException("No change of driver was made to order: " + truckingId + ". It maybe the same driver of before the change.");
    }

    public void updateDateOnTrucking(int truckingId, LocalDateTime date) throws Exception {
        checkDate(date);
        //TODO: it's really complicated, maybe we need to delete that option?
    }

    private void checkIfActiveUserIsTruckManager() throws Exception {
        if (getActiveUser().hashCode() == getNullUserForLogOut().hashCode())
            throw new Exception("There is no user connected");
        if (getActiveUser().getRole() != Role.truckingManager | !(getActiveUser() instanceof TruckManager))
            throw new Exception("Oops, you are not a truck manager");
    }

    private String printTrucking(TruckingDTO trucking) throws Exception {
        String toReturn = "TRUCKING - " + trucking.getId() + "\n\n";
        toReturn += "TRUCKING DETAILS:\n";
        toReturn += "Date: " + trucking.getDate().getDayOfMonth() + "/" + trucking.getDate().getMonthValue() + "/" + trucking.getDate().getYear() + "\n";
        toReturn += "Start Hour: " + printHour(trucking.getDate()) + "\n";
        toReturn += "End Hour: " + printHour(trucking.getDate().plusHours(trucking.getHours()).plusMinutes(trucking.getMinutes())) + "\n";
        toReturn += "Vehicle registration plate: " + trucking.getVehicleRegistrationPlate() + "\n";
        toReturn += "Driver: " + trucking.getDriverUsername() + "\n";
        toReturn += printSources(trucking.getId());
        toReturn += printDestinations(trucking.getId());
        toReturn += printProducts(trucking.getId());
        if (trucking.getWeight() > 0)
            toReturn += "Total weight: " + trucking.getWeight() + "\n";
        else
            toReturn += "There is no data about the trucking weight\n";
        return toReturn;
    }

    private String printHour(LocalDateTime date) {
        String toReturn = "";
        if(date.getHour()<10)
            toReturn += "0" + date.getHour() + ":";
        else
            toReturn += date.getHour() + ":";
        if(date.getMinute()<10)
            toReturn += "0" + date.getMinute();
        else
            toReturn += date.getHour();
        return toReturn;
    }

    private String printSources(int TruckingID) throws Exception {
        String toReturn = "\nSOURCE DETAILS:\n";
        toReturn += printSitesList(sourcesMapper.getSourcesByTruckingId(TruckingID));
        return toReturn;
    }

    public synchronized String printDestinations(int TruckingID) throws Exception {
        String toReturn = "\nDESTINATION DETAILS:\n";
        toReturn += printSitesList(truckings_destsMapper.getDestinationsByTruckingId(TruckingID));
        return toReturn;
    }

    public synchronized String printProducts(int TruckingID) {
        return ""; //TODO: need to implement function that get products by truckingID
    }

    private String printSitesList(List<Site> sourcesOrDestinations) {
        String toReturn  = "";
        int siteCounter = 1;
        for (Site site : sourcesOrDestinations) {
            toReturn += siteCounter + ". " + site.printSite();
            siteCounter++;
        }
        return toReturn;
    }

    private boolean checkDate(LocalDateTime date) {
        if (date.compareTo(LocalDateTime.now()) <= 0)
            throw new IllegalArgumentException("Oops, the date must be in the future");
        return true;
    }

}
